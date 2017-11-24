package com.paucls.runbookDDD.application.runbook

data class CreateRunbook(val name: String, val ownerId: String)

data class CompleteRunbook(val runbookId: String, val userId: String)

data class AddTask(val runbookId: String, val name: String, val description: String, val assigneeId: String)

data class StartTask(val runbookId: String, val taskId: String, val userId: String)

data class CompleteTask(val runbookId: String, val taskId: String, val userId: String)
