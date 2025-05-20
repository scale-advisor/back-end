package org.scaleadvisor.backend.global.email.service

import jakarta.mail.internet.MimeMessage
import org.scaleadvisor.backend.global.config.SecurityConfig
import org.scaleadvisor.backend.global.email.constant.EmailTitleConstant
import org.scaleadvisor.backend.global.email.dto.ConfirmMailRequest
import org.scaleadvisor.backend.global.email.dto.ConfirmSignupRequest
import org.scaleadvisor.backend.global.email.dto.PwdResetConfirmRequest
import org.scaleadvisor.backend.global.email.dto.PwdResetRequest
import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.EmailTokenGoneException
import org.scaleadvisor.backend.global.exception.model.MessagingException
import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class EmailService(
    private val userRepository: UserRepository,
    private val javaMailSender: JavaMailSender,
    private val redisTemplate: RedisTemplate<String, String>,
    private val securityConfig: SecurityConfig
) {

    @Value("\${spring.mail.username}")
    private lateinit var serviceName: String

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

    fun sendConfirmationEmail(request: ConfirmMailRequest) {
        val token = generateMailToken(prefix = "signup:token", email = request.email, duration = 1)
        val confirmLink = "${request.confirmSignupUrl}/apis/auth?email=${request.email}&token=$token"

        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>해당 링크를 통해 회원가입을 완료하세요:</p>")
            append("<a href=\"$confirmLink\">회원가입 인증</a>")
            append("<br><p>링크는 24시간 동안 유효합니다.</p>")
        }
        sendMail(serviceName, request.email, content, EmailTitleConstant.SIGNUP_TITLE)
    }

    fun sendResetPasswordEmail(request: PwdResetRequest){
        val requestEmail = request.email
        if (!userRepository.existsByEmail(requestEmail)) {
            throw NotFoundException("해당 이메일의 유저가 존재하지 않습니다.")
        }

        val token = generateMailToken(
            prefix = "password-reset:token",
            email = request.email,
            duration = 10,
            unit = TimeUnit.MINUTES
        )

        val resetLink = "${request.pwdResetRedirectUrl}/apis/auth/reset-password-email?email=$requestEmail&token=$token"
        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>아래 링크를 클릭하여 비밀번호를 재설정하세요.(10분 동안 유효합니다):</p>")
            append("<a href=\"$resetLink\">비밀번호 재설정하기</a>")
        }

        sendMail(serviceName, request.email, content, EmailTitleConstant.RESET_CREDENTIAL_TITLE)
    }

    fun confirmSignup(request: ConfirmSignupRequest) {
        val key = "signup:token:${request.token}"
        val storedEmail = valOps().get(key)
        if (storedEmail == request.email) {
            redisTemplate.delete(key)
            userRepository.updateConfirmedByEmail(request.email)
        } else {
            throw EmailTokenGoneException("이메일 혹은 이메일 토큰이 잘못 되었습니다.")
        }
    }

    fun resetPassword(request: PwdResetConfirmRequest) {
        val key = "password-reset:token:${request.token}"
        val storedEmail = valOps().get(key)
        if (redisTemplate.hasKey(request.token) && storedEmail != null) {
            throw EmailTokenGoneException(UserMessageConstant.EMAIL_TOKEN_GONE_MESSAGE)
        } else{
            val newEncodedPassword = securityConfig.passwordEncoder().encode(request.newPassword)
            userRepository.resetPasswordByEmail(storedEmail!!, newEncodedPassword)
            redisTemplate.delete(key)
        }
    }
}
