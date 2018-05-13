package com.paucls.runbookDDD.application.runbook

sealed class RunbookCommand(val userId: String) {
    class CreateRunbook(val name: String, userId: String) : RunbookCommand(userId)

    class CompleteRunbook(val runbookId: String, userId: String) : RunbookCommand(userId)

    class AddTask(val runbookId: String, val name: String, val description: String,
                  val assigneeId: String?, userId: String) : RunbookCommand(userId)

    class StartTask(val runbookId: String, val taskId: String, userId: String) : RunbookCommand(userId)

    class CompleteTask(val runbookId: String, val taskId: String, userId: String) : RunbookCommand(userId)
}
