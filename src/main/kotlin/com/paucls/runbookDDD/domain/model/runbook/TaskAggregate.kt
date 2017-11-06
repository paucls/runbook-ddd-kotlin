package com.paucls.runbookDDD.domain.model.runbook


class TaskAggregate(val taskId: String, val name: String, val description: String, val userId: String) {
    companion object {
        val OPEN = "OPEN"
        val IN_PROGRESS = "IN_PROGRESS"
        val CLOSED = "CLOSED"
    }

    private var status = OPEN

    fun startTask() {
        status = IN_PROGRESS
    }

    fun isInProgress(): Boolean = status == IN_PROGRESS
}