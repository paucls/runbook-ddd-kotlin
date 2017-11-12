package com.paucls.runbookDDD.application.runbook

data class CreateRunbookCommand(val name: String, val ownerId: String)