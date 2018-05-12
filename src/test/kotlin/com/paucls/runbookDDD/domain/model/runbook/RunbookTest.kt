package com.paucls.runbookDDD.domain.model.runbook

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith

class RunbookTest {

    private val RUNBOOK_ID = "runbook-id"
    private val RUNBOOK_NAME = "runbook-name"
    private val OWNER_ID = "owner-id"
    private val TASK_ID = "task-id"
    private val TASK_NAME = "task-name"
    private val TASK_DESCRIPTION = "task-description"
    private val TASK_ASSIGNEE_ID = "assignee-id"

    @Test
    fun `can create runbook`() {
        val newRunbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        assertThat(newRunbook.runbookId).isEqualTo(RUNBOOK_ID)
        assertThat(newRunbook.name).isEqualTo(RUNBOOK_NAME)
        assertThat(newRunbook.ownerId).isEqualTo(OWNER_ID)
        assertThat(newRunbook.isCompleted()).isFalse()
    }

    @Test
    fun `can add task`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks.size).isOne()
        assertThat(runbook.domainEvents()).containsExactly(TaskAdded(TASK_ID, TASK_NAME, TASK_ASSIGNEE_ID))
    }

    @Test
    fun `can start task`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        runbook.startTask(TASK_ID, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks[TASK_ID]?.isInProgress()).isTrue()
    }

    @Test
    fun `cannot start task assigned to different user`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        assertFailsWith<TaskAssignedToDifferentUserException> {
            runbook.startTask(TASK_ID, "no-assignee-user-id")
        }
    }

    @Test
    fun `can complete task`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)
        runbook.startTask(TASK_ID, TASK_ASSIGNEE_ID)

        runbook.completeTask(TASK_ID, TASK_ASSIGNEE_ID)

        assertThat(runbook.tasks[TASK_ID]?.isCompleted()).isTrue()
    }

    @Test
    fun `cannot complete task that is not in progress`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        assertFailsWith<CanOnlyCompleteInProgressTaskException> {
            runbook.completeTask(TASK_ID, TASK_ASSIGNEE_ID)
        }
    }

    @Test
    fun `can reject task`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        runbook.rejectTask(TASK_ID, OWNER_ID)

        assertThat(runbook.tasks[TASK_ID]?.isRejected()).isTrue()
    }

    @Test
    fun `cannot complete runbook if not the owner`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        assertFailsWith<RunbookOwnedByDifferentUserException> {
            runbook.completeRunbook("no-owner-user-id")
        }
    }

    @Test
    fun `can complete empty runbook`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)

        runbook.completeRunbook(OWNER_ID)

        assertThat(runbook.isCompleted()).isTrue()
    }

    @Test
    fun `cannot complete runbook with pending tasks`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)

        assertFailsWith<RunBookWithPendingTasksException> {
            runbook.completeRunbook(OWNER_ID)
        }
    }

    @Test
    fun `can complete with all tasks completed`() {
        val runbook = Runbook(RUNBOOK_ID, RUNBOOK_NAME, OWNER_ID)
        runbook.addTask(TASK_ID, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID)
        runbook.startTask(TASK_ID, TASK_ASSIGNEE_ID)
        runbook.completeTask(TASK_ID, TASK_ASSIGNEE_ID)

        runbook.completeRunbook(OWNER_ID)

        assertThat(runbook.isCompleted()).isTrue()
    }

}