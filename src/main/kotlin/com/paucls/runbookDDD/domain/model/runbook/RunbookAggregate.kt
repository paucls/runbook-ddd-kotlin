package com.paucls.runbookDDD.domain.model.runbook

class RunbookAggregate(val runbookId: String, val name: String, val ownerId: String) {

    val tasks = HashMap<String, TaskAggregate>()

    fun addTask(taskId: String, name: String, description: String, userId: String) {
        tasks.put(taskId, TaskAggregate(taskId, name, description, userId))
    }

    fun startTask(taskId: String, userId: String) {
        tasks[taskId]?.startTask()
    }

}
