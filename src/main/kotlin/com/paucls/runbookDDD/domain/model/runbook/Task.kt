package com.paucls.runbookDDD.domain.model.runbook

/**
 * Task entity is part of the Runbook aggregate.
 */
class Task(
        val taskId: String,
        val name: String,
        val description: String,
        var assigneeId: String?
) {
    private var state: TaskState = TaskState.OPEN

    fun assign(assigneeId: String) {
        this.assigneeId = assigneeId
    }

    fun startTask(userId: String) {
        if (assigneeId == null) {
            throw UnassignedTaskCannotBeStartedException()
        }
        if (userId != assigneeId) {
            throw TaskAssignedToDifferentUserException()
        }

        state = TaskState.IN_PROGRESS
    }

    fun completeTask() {
        if (!isInProgress()) {
            throw CanOnlyCompleteInProgressTaskException()
        }

        state = TaskState.COMPLETED
    }

    fun rejectTask() {
        state = TaskState.REJECTED
    }

    fun isInProgress(): Boolean = state == TaskState.IN_PROGRESS

    fun isCompleted(): Boolean = state == TaskState.COMPLETED

    fun isRejected(): Boolean = state == TaskState.REJECTED
}

private enum class TaskState {
    OPEN,
    IN_PROGRESS,
    COMPLETED,
    REJECTED,
}
