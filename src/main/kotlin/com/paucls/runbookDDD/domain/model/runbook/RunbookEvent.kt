package com.paucls.runbookDDD.domain.model.runbook

import com.paucls.runbookDDD.domain.model.DomainEvent

sealed class RunbookEvent : DomainEvent

data class TaskAdded(val taskId: String, val name: String, val assigneeId: String) : RunbookEvent()
