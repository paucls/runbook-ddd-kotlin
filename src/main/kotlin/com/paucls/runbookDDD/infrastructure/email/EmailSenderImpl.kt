package com.paucls.runbookDDD.infrastructure.email

import com.paucls.runbookDDD.application.EmailSender
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Fake EmailSender implementation
 */
@Service
class EmailSenderImpl : EmailSender {
    private val logger = LoggerFactory.getLogger(EmailSender::class.java)

    override fun sendEmail(toUserId: String, title: String, body: String) {
        logger.info(">> Sending email: $toUserId - $title - $body")
    }
}