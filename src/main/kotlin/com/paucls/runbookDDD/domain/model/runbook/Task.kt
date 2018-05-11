package com.paucls.runbookDDD.domain.model.runbook

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * Task entity is part of the Runbook aggregate.
 */
@RedisHash("Task")
class Task(
        @Id
        val taskId: String,
        val name: String,
        val description: String,
        val assigneeId: String
) {
    // TODO: Create a an enum or a type for the states
    companion object {
        val OPEN = "OPEN"
        val IN_PROGRESS = "IN_PROGRESS"
        val COMPLETED = "COMPLETED"
        val REJECTED = "REJECTED"
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

    fun rejectTask() {
        status = REJECTED
    }

    fun isInProgress(): Boolean = status == IN_PROGRESS

    fun isCompleted(): Boolean = status == COMPLETED

    fun isRejected(): Boolean = status == REJECTED
}