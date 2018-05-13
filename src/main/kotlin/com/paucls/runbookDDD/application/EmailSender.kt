package com.paucls.runbookDDD.application

interface EmailSender {
    fun sendEmail(toUserId: String, title: String, body: String)
}