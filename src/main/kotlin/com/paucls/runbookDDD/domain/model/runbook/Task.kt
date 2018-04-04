package com.paucls.runbookDDD.domain.model.runbook

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Task entity is part of the Runbook aggregate.
 */
@Entity
class Task(
        @Id
        val taskId: String,
        val name: String,
        val description: String,
        val assigneeId: String
) {
    companion object {
        val OPEN = "OPEN"
        val IN_PROGRESS = "IN_PROGRESS"
        val COMPLETED = "COMPLETED"
        val REJECTED = "REJECTED"
    }

    // Used by JPA
    @Column(name = "runbook_id")
    private lateinit var runbookId: String

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