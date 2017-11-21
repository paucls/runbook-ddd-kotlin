package com.paucls.runbookDDD.application.runbook

data class CreateRunbook(val name: String, val ownerId: String)

data class AddTask(val runbookId: String, val name: String, val description: String, val assigneeId: String)

