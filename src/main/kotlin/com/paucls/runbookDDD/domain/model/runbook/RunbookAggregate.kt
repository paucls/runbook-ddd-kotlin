package com.paucls.runbookDDD.domain.model.runbook

class RunbookAggregate(
        val runbookId: String,
        val name: String,
        val ownerId: String
) {
    companion object {
        val OPEN = "OPEN"
        val COMPLETED = "COMPLETED"
    }

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

    fun completeRunbook(userId: String) {
        val hasPendingTask = tasks.values.any { !it.isCompleted() }
        if (hasPendingTask) {
            throw RunBookWithPendingTasksException()
        }

        status = COMPLETED
    }
}
