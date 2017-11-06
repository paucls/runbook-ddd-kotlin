package com.paucls.runbookDDD.domain.model.runbook

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RunbookAggregateTest {

    private val RUNBOOK_ID = "runbook-id"
    private val RUNBOOK_NAME = "runbook-name"
    private val OWNER_ID = "owner-id"
    private val TASK_ID = "task-id"
    private val TASK_NAME = "task-name"
    private val TASK_DESCRIPTION = "task-description"
    private val USER_ID = "user-id"

    @Test
    fun `can create runbook`() {
        val newRunbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        assertThat(newRunbook.runbookId).isEqualTo(RUNBOOK_ID)
        assertThat(newRunbook.name).isEqualTo(RUNBOOK_NAME)
        assertThat(newRunbook.ownerId).isEqualTo(OWNER_ID)
    }

    @Test
    fun `can add task`() {
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, USER_ID)

        assertThat(runbook.tasks.size).isOne()
    }

    @Test
    fun `can start task`() {
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, USER_ID)

        runbook.startTask(TASK_ID, USER_ID)

        assertThat(runbook.tasks[TASK_ID]?.isInProgress()).isTrue()
    }

}