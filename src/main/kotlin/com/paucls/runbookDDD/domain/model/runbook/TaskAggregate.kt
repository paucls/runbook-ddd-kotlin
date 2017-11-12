package com.paucls.runbookDDD.domain.model.runbook

class TaskAggregate(
        val taskId: String,
        val name: String,
        val description: String,
        val assigneeId: String
) {
    companion object {
        val OPEN = "OPEN"
        val IN_PROGRESS = "IN_PROGRESS"
        val COMPLETED = "COMPLETED"
    }

    private var status = OPEN

    fun startTask(userId: String) {
        if (userId != assigneeId) {
            throw TaskAssignedToDifferentUserException()
        }

        status = IN_PROGRESS
    }

    fun completeTask() {
        if (!isInProgress()) {
            throw CanOnlyCompleteInProgressTaskException()
        }

        status = COMPLETED
    }

    fun isInProgress(): Boolean = status == IN_PROGRESS

    fun isCompleted(): Boolean = status == COMPLETED
}