package com.paucls.runbookDDD.application.runbook

sealed class RunbookCommand {
    class CreateRunbook(val name: String, val ownerId: String) : RunbookCommand()

    class CompleteRunbook(val runbookId: String, val userId: String) : RunbookCommand()

    class AddTask(val runbookId: String, val name: String, val description: String, val assigneeId: String) : RunbookCommand()

    class StartTask(val runbookId: String, val taskId: String, val userId: String) : RunbookCommand()

    class CompleteTask(val runbookId: String, val taskId: String, val userId: String) : RunbookCommand()
}
