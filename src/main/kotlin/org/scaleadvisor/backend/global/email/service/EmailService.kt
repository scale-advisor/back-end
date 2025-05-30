package org.scaleadvisor.backend.global.email.service

import jakarta.mail.internet.MimeMessage
import org.scaleadvisor.backend.global.config.SecurityConfig
import org.scaleadvisor.backend.global.email.constant.EmailTitleConstant
import org.scaleadvisor.backend.global.email.dto.*
import org.scaleadvisor.backend.global.email.repository.InvitationEmailRepository
import org.scaleadvisor.backend.global.exception.constant.UserMessageConstant
import org.scaleadvisor.backend.global.exception.model.*
import org.scaleadvisor.backend.global.security.CurrentUserIdExtractor
import org.scaleadvisor.backend.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.TimeUnit

@Service
@Transactional
class EmailService(
    private val userRepository: UserRepository,
    private val javaMailSender: JavaMailSender,
    private val redisTemplate: RedisTemplate<String, String>,
    private val securityConfig: SecurityConfig,
    private val invitationEmailRepository: InvitationEmailRepository
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
        val confirmLink = "${request.confirmSignupUrl}/auth?email=${request.email}&token=$token"

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

        val resetLink = "${request.pwdResetRedirectUrl}/auth/reset-password-email?email=$requestEmail&token=$token"
        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>아래 링크를 클릭하여 비밀번호를 재설정하세요.(10분 동안 유효합니다):</p>")
            append("<a href=\"$resetLink\">비밀번호 재설정하기</a>")
        }

        sendMail(serviceName, request.email, content, EmailTitleConstant.RESET_CREDENTIAL_TITLE)
    }

    fun sendInvitaionEmail(request: InvitationMailRequest, projectId: Long) {
        val currentUser = CurrentUserIdExtractor.getCurrentUserIdFromSecurity()
            ?: throw UnauthorizedException("인증 정보가 없습니다.")

        if (!invitationEmailRepository.isOwner(currentUser, projectId)) {
            throw ForbiddenException("프로젝트 소유자만 초대 이메일을 발송할 수 있습니다.")
        }

        val requestEmail = request.email
        if (!userRepository.existsByEmail(requestEmail)) {
            throw NotFoundException("해당 이메일의 유저가 존재하지 않습니다.")
        }

        val token = generateMailToken(prefix = "invitation:token", email = request.email, duration = 1)
        val invitationLink = "${request.invitationUrl}/invitation/accept?projectId=$projectId&email=$requestEmail&token=$token"

        val content = buildString {
            append("<p>안녕하세요, $serviceName 입니다.</p>")
            append("<p>아래 링크를 클릭하여 프로젝트 초대를 수락하세요.(10분 동안 유효합니다):</p>")
            append("<a href=\"$invitationLink\">프로젝트 들어가기</a>")
        }

        sendMail(serviceName, request.email, content, EmailTitleConstant.INVITATION_TITLE)
        invitationEmailRepository.inviteByEmail(requestEmail, projectId)
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

    fun acceptInvitation(projectId: Long, email: String, token: String): AcceptInvitationResponse {
        val key = "invitation:token:${token}"
        val storedEmail = valOps().get(key)
        if (storedEmail == email) {
            redisTemplate.delete(key)
            invitationEmailRepository.acceptInvitation(email, projectId)

            return AcceptInvitationResponse(
                projectId = projectId.toString()
            )

        } else {
            throw EmailTokenGoneException("이메일 혹은 이메일 토큰이 잘못 되었습니다.")
        }
    }
}
