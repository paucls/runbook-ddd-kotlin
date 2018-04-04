package com.paucls.runbookDDD.domain.model.runbook

import javax.persistence.*

/**
 * The root entity of the Runbook aggregate - Aggregate Root.
 */
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

    private var status = OPEN

    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @JoinColumn(name = "runbook_id")
    val tasks: MutableMap<String, Task> = HashMap()

    fun isCompleted() = status == COMPLETED

    fun addTask(taskId: String, name: String, description: String, assigneeId: String) {
        tasks[taskId] = Task(taskId, name, description, assigneeId)
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
