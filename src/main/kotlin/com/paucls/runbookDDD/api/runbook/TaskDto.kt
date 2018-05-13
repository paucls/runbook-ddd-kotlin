package com.paucls.runbookDDD.api.runbook

data class TaskDto(
        val taskId: String?,
        val name: String = "",
        val description: String = "",
        val assigneeId: String? = null
)