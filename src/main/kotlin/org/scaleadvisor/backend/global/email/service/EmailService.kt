package org.scaleadvisor.backend.global.email.service

import jakarta.mail.internet.MimeMessage
import org.scaleadvisor.backend.global.email.constant.EmailTitleConstant
import org.scaleadvisor.backend.global.email.dto.PwdResetRequest
import org.scaleadvisor.backend.global.exception.constant.TokenMessageConstant
import org.scaleadvisor.backend.global.exception.model.MessagingException
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

@Service
class EmailService(
    private val userRepository: UserRepository,
    private val javaMailSender: JavaMailSender,
    private val redisTemplate: RedisTemplate<String, String>
) {

    @Value("\${spring.mail.username}")
    private lateinit var serviceName: String

    @Value("\${app.url}")
    private val appUrl: String = ""

    private fun valOps(): ValueOperations<String, String> =
        redisTemplate.opsForValue()

    fun generateMailToken(prefix: String,
                          email: String,
                          duration: Long,
                          unit: TimeUnit = TimeUnit.HOURS): String {
        val token = UUID.randomUUID().toString()
        val key = "$prefix:$token"
        valOps().set(key, email, duration, unit)
        return token
    }

    private fun sendMail(setFrom: String, toMail: String, content: String, title: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()

        try {
            MimeMessageHelper(message, true, "utf-8").apply {
                setFrom(setFrom)
                setTo(toMail)
                setSubject(title)
                setText(content, true)
            }
            javaMailSender.send(message)
        } catch (e: MessagingException) {
            throw MessagingException("메시지 전송 중 오류가 발생했습니다.")
        }
    }

    fun sendConfirmationEmail(email: String) {
        val token = generateMailToken(prefix = "signup:token", email = email, duration = 1)
        val confirmLink = "$appUrl/email-verification?email=$email&token=$token"

        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>해당 링크를 통해 회원가입을 완료하세요:</p>")
            append("<a href=\"$confirmLink\">회원가입 인증</a>")
            append("<br><p>링크는 24시간 동안 유효합니다.</p>")
        }
        sendMail(serviceName, email, content, EmailTitleConstant.SIGNUP_TITLE)
    }

    fun sendResetPasswordEmail(request: PwdResetRequest): ResponseEntity<String> {
        if (!userRepository.existsByEmail(request.email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록되지 않은 이메일입니다.")
        }

        val token = generateMailToken(
            prefix = "password-reset:token",
            email = request.email,
            duration = 10,
            unit = TimeUnit.MINUTES
        )

        val resetLink = "${appUrl}/password-reset?token=$token"
        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>아래 링크를 클릭하여 비밀번호를 재설정하세요.(10분 동안 유효합니다):</p>")
            append("<a href=\"$resetLink\">비밀번호 재설정하기</a>")
        }

        sendMail(serviceName, request.email, content, EmailTitleConstant.RESET_PWD_TITLE)
        return ResponseEntity.ok().body("비밀번호 재설정 이메일을 발송했습니다.")
    }

    fun confirmSignup(email: String, token: String): ResponseEntity<String> {
        val key = "signup:token:$token"
        val storedEmail = valOps().get(key)
        return if (storedEmail == email) {
            redisTemplate.delete(key)
            userRepository.updateConfirmedByEmail(email)
            ResponseEntity.ok().body("회원가입 인증이 완료되었습니다.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TokenMessageConstant.INVALID_TOKEN_MESSAGE)
        }
    }
}
