package com.paucls.runbookDDD.domain.model.runbook

class RunbookAggregate(
        val runbookId: String,
        val name: String,
        val ownerId: String
) {
    val tasks = HashMap<String, TaskAggregate>()
    val isCompleted = false

    fun addTask(taskId: String, name: String, description: String, assigneeId: String) {
        tasks.put(taskId,
                TaskAggregate(taskId, name, description, assigneeId))
    }

    fun startTask(taskId: String, userId: String) {
        tasks[taskId]?.startTask()
    }

    fun completeTask(taskId: String, userId: String) {
        tasks[taskId]?.completeTask()
    }
}
