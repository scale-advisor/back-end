package org.scaleadvisor.backend.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class EmailConfig {
    @Value("\${spring.mail.username}")
    private val username: String? = null

    @Value("\${spring.mail.password}")
    private val password: String? = null

    @Value("\${spring.mail.host}")
    private val host: String? = null

    @Value("\${spring.mail.port}")
    private var port: Int = 0

    @Bean
    fun mailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password

        val javaMailProperties = Properties()
        javaMailProperties["mail.transport.protocol"] = "smtp"
        javaMailProperties["mail.smtp.auth"] = "true"
        javaMailProperties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        javaMailProperties["mail.smtp.starttls.enable"] = "true"
        javaMailProperties["mail.debug"] = "true"
        javaMailProperties["mail.smtp.ssl.trust"] = host
        javaMailProperties["mail.smtp.ssl.protocols"] = "TLSv1.3"

        mailSender.javaMailProperties = javaMailProperties

        return mailSender
    }
}