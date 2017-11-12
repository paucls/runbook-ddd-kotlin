package com.paucls.runbookDDD.domain.model.runbook

import com.paucls.runbookDDD.api.runbook.TaskAssignedToDifferentUserException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class RunbookAggregateTest {

    private val RUNBOOK_ID = "runbook-id"
    private val RUNBOOK_NAME = "runbook-name"
    private val OWNER_ID = "owner-id"
    private val TASK_ID = "task-id"
    private val TASK_NAME = "task-name"
    private val TASK_DESCRIPTION = "task-description"
    private val TASK_ASSIGNEE_ID = "assignee-id"

    @Rule
    @JvmField
    val exception = ExpectedException.none()

    @Test
    fun `can create runbook`() {
        val newRunbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        assertThat(newRunbook.runbookId).isEqualTo(RUNBOOK_ID)
        assertThat(newRunbook.name).isEqualTo(RUNBOOK_NAME)
        assertThat(newRunbook.ownerId).isEqualTo(OWNER_ID)
        assertThat(newRunbook.isCompleted).isFalse()
    }

    @Test
    fun `can add task`() {
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks.size).isOne()
    }

    @Test
    fun `can start task`() {
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        runbook.startTask(TASK_ID, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks[TASK_ID]?.isInProgress()).isTrue()
    }

    @Test
    fun `cannot start task assigned to different user`() {
        // Given
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        exception.expect(TaskAssignedToDifferentUserException::class.java)

        // When
        runbook.startTask(TASK_ID, "user-id-2")
    }

    @Test
    fun `can complete task`() {
        val runbook = RunbookAggregate(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)
        runbook.startTask(TASK_ID, TASK_ASSIGNEE_ID)

        runbook.completeTask(TASK_ID, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks[TASK_ID]?.isCompleted()).isTrue()
    }

}