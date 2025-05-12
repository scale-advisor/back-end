package org.scaleadvisor.backend.global.email.service

import jakarta.mail.internet.MimeMessage
import org.scaleadvisor.backend.global.exception.constant.TokenMessageConstant
import org.scaleadvisor.backend.global.exception.model.MessagingException
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,
    private val redisTemplate: RedisTemplate<String, String>
) {

    @Value("\${spring.mail.username}")
    private lateinit var serviceName: String

    fun generateSignupToken(email: String): String {
        val token = UUID.randomUUID().toString()
        val valOps: ValueOperations<String, String> = redisTemplate.opsForValue()
        valOps.set("signup:token:$token", email, 24, TimeUnit.HOURS)
        return token
    }

    fun sendConfirmationEmail(email: String, appUrl: String) {
        val token = generateSignupToken(email)
        val confirmLink = "$appUrl/email-verification?email=$email&token=$token"
        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>해당 링크를 통해 회원가입을 완료하세요:</p>")
            append("<a href=\"$confirmLink\">회원가입 인증</a>")
            append("<br><p>링크는 24시간 동안 유효합니다.</p>")
        }
        sendMail(serviceName, email, content)
    }

    private fun sendMail(setFrom: String, toMail: String, content: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val title = "Scale-Advisor 회원가입 인증 메일입니다."

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

    fun confirmSignup(email: String, token: String): ResponseEntity<String> {
        val valOps: ValueOperations<String, String> = redisTemplate.opsForValue()
        val key = "signup:token:$token"
        val storedEmail = valOps.get(key)
        return if (storedEmail == email) {
            redisTemplate.delete(key)
            ResponseEntity.ok("회원가입 인증이 완료되었습니다.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TokenMessageConstant.INVALID_TOKEN_MESSAGE)
        }
    }
}
