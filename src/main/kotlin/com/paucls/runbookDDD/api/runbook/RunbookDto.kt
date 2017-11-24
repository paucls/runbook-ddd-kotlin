package com.paucls.runbookDDD.api.runbook

data class RunbookDto(
    var runbookId: String = "",
    var name: String = "",
    var ownerId: String = "",
    var isCompleted: Boolean = false
)
