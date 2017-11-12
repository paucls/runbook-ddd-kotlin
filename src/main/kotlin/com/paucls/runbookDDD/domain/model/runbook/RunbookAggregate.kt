package com.paucls.runbookDDD.domain.model.runbook

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class RunbookAggregate(
        @Id
        val runbookId: String,
        val name: String,
        val ownerId: String
) {
    companion object {
        val OPEN = "OPEN"
        val COMPLETED = "COMPLETED"
    }

    // TODO: using kotlin-jpa plugin it should not be needed a default constructor
    private constructor() : this("", "", "")

    private var status = OPEN
    val tasks = HashMap<String, TaskAggregate>()

    fun isCompleted() = status == COMPLETED

    fun addTask(taskId: String, name: String, description: String, assigneeId: String) {
        tasks.put(taskId,
                TaskAggregate(taskId, name, description, assigneeId))
    }

    fun startTask(taskId: String, userId: String) {
        tasks[taskId]?.startTask(userId)
    }

    fun completeTask(taskId: String, userId: String) {
        tasks[taskId]?.completeTask()
    }

    fun rejectTask(taskId: String, userId: String) {
        tasks[taskId]?.rejectTask()
    }

    fun completeRunbook(userId: String) {
        if (userId != ownerId) {
            throw RunbookOwnedByDifferentUserException()
        }

        val hasPendingTask = tasks.values.any { !it.isCompleted() }
        if (hasPendingTask) {
            throw RunBookWithPendingTasksException()
        }

        status = COMPLETED
    }
}
