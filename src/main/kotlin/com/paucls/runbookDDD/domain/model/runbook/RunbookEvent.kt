package com.paucls.runbookDDD.domain.model.runbook

import com.paucls.runbookDDD.domain.model.DomainEvent

sealed class RunbookEvent : DomainEvent

data class TaskAdded(
        val runbookId: String,
        val taskId: String) : RunbookEvent()

data class TaskAssigned(
        val runbookId: String,
        val taskId: String,
        val assigneeId: String,
        val name: String) : RunbookEvent()
