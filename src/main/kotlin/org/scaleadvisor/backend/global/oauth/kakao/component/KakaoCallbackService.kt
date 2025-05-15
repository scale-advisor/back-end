package org.scaleadvisor.backend.global.oauth.kakao.component

import org.slf4j.LoggerFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.scaleadvisor.backend.global.exception.model.KakaoBadRequestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URLEncoder

@Component
class KakaoCallbackService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private val log: org.slf4j.Logger? = LoggerFactory.getLogger(KakaoCallbackService::class.java)
    }

    @Value("\${kakao.api-key}")
    private val kakaoApiKey: String? = null

    @Value("\${kakao.login-redirect-url}")
    private val kakaoLoginRedirectUrl: String? = null

    @Value("\${kakao.authorize-url}")
    private lateinit var kakaoAuthorizeUrl:String

    @Value("\${kakao.code-callback-url}")
    private lateinit var kakaoCodeUrl: String

    @Value("\${kakao.userInfo-callback-url}")
    private lateinit var kakaoUserInfoUrl: String

    @Value("\${kakao.token-expire-url}")
    private lateinit var kakaoExpireTokenUrl: String

    fun getKakaoAuthorizeUrl(): String {
        val kakaoAuthUrl = UriComponentsBuilder
            .fromHttpUrl(kakaoAuthorizeUrl)
            .queryParam("response_type", "code")
            .queryParam("client_id", kakaoApiKey)
            .queryParam("redirect_uri", kakaoLoginRedirectUrl)
            .build()
            .encode()
            .toUriString()

        return kakaoAuthUrl
    }

    fun getKakaoToken(code: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body = buildString {
            append("grant_type=authorization_code")
            append("&client_id=").append(kakaoApiKey)
            append("&redirect_uri=").append(URLEncoder.encode(kakaoLoginRedirectUrl, "UTF-8"))
            append("&code=").append(URLEncoder.encode(code, "UTF-8"))
        }

        val requestEntity = HttpEntity(body, headers)

        val responseEntity: ResponseEntity<String> =
            restTemplate.postForEntity(kakaoCodeUrl, requestEntity, String::class.java)

        val statusValue = responseEntity.statusCode

        if (!statusValue.is2xxSuccessful) {
            throw KakaoBadRequestException("카카오 토큰을 제대로 불러오지 못함: ${statusValue.value()}")
        }

        val rootNode = objectMapper.readTree(responseEntity.body)
        log?.info("[KakaoAccessToken] kakao_token = {}", rootNode.get("access_token").asText())
        return rootNode.get("access_token").asText()
    }

    fun getUserInfoFromKakaoToken(kakaoAccessToken: String): Map<String, Any> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            setBearerAuth(kakaoAccessToken)
        }

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        val requestEntity = HttpEntity(body, headers)

        val response: ResponseEntity<String> =
            restTemplate.postForEntity(kakaoUserInfoUrl, requestEntity, String::class.java)

        if (!response.statusCode.is2xxSuccessful) {
            throw KakaoBadRequestException("카카오 사용자 정보 요청 실패: ${response.statusCode.value()}")
        }

        val json = response.body ?: throw RuntimeException("Empty response from Kakao")

        val rootNode = objectMapper.readTree(json)
        val id = rootNode.get("id").asText()
        val nickname = rootNode
            .path("properties")
            .get("nickname")
            .asText()
        val email = rootNode
            .path("kakao_account")
            .get("email")
            .asText()

        return mapOf(
            "id" to id,
            "nickname" to nickname,
            "email" to email
        )
    }

    fun expireKakaoToken(kakaoAccessToken: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            setBearerAuth(kakaoAccessToken)
        }

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        val requestEntity = HttpEntity(body, headers)

        val response: ResponseEntity<String> =
            restTemplate.postForEntity(kakaoExpireTokenUrl, requestEntity, String::class.java)

        if (!response.statusCode.is2xxSuccessful) {
            throw KakaoBadRequestException("카카오 로그아웃 요청 실패: ${response.statusCode.value()}")
        }

        val rootNode = objectMapper.readTree(response.body ?: throw KakaoBadRequestException("카카오로 부터 빈 값 반환."))
        val kakaoUserId = rootNode.get("id").asText()

        log?.info("[KakaoLogout] user_id = {}", kakaoUserId)
        return kakaoUserId
    }
}