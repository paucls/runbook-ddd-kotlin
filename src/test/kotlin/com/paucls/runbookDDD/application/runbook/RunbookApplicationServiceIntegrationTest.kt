package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.application.runbook.RunbookCommand.*;
import com.paucls.runbookDDD.persistence.RunbookRepository
import com.paucls.runbookDDD.persistence.TaskRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class RunbookApplicationServiceIntegrationTest {

    private val RUNBOOK_NAME = "runbook-name"
    private val OWNER_ID = "owner-id"
    private val TASK_NAME = "task-name"
    private val TASK_DESCRIPTION = "task-description"
    private val TASK_ASSIGNEE_ID = "assignee-id"

    @Autowired
    private lateinit var runbookRepository: RunbookRepository

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var runbookApplicationService: RunbookApplicationService

    @Before
    fun setup() {
        taskRepository.deleteAll()
        runbookRepository.deleteAll()
    }

    @Test
    fun `create runbook`() {
        // When
        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(RUNBOOK_NAME, OWNER_ID))

        // Then
        val newRunbook = runbookRepository.findById(runbookId).get()
        assertThat(newRunbook).isNotNull()
        assertThat(newRunbook.name).isEqualTo(RUNBOOK_NAME)
        assertThat(newRunbook.ownerId).isEqualTo(OWNER_ID)
    }

    @Test
    fun `add task`() {
        // Given
        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(RUNBOOK_NAME, OWNER_ID))

        // When
        val taskId = runbookApplicationService.addTask(AddTask(runbookId, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID))

        // Then
        val runbook = runbookRepository.findById(runbookId).get()
        assertThat(runbook.tasks).hasSize(1)
        val newTask = runbook.tasks[taskId]
        assertThat(newTask?.name).isEqualTo(TASK_NAME)
        assertThat(newTask?.description).isEqualTo(TASK_DESCRIPTION)
    }

    @Test
    fun `complete task`() {
        // Given
        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(RUNBOOK_NAME, OWNER_ID))
        val taskId = runbookApplicationService.addTask(AddTask(runbookId, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID))
        runbookApplicationService.startTask(StartTask(runbookId, taskId, TASK_ASSIGNEE_ID))

        // When
        runbookApplicationService.completeTask(CompleteTask(runbookId, taskId, TASK_ASSIGNEE_ID))

        // Then
        val task = taskRepository.findById(taskId).get()
        assertThat(task.name).isEqualTo(TASK_NAME)
        assertThat(task.description).isEqualTo(TASK_DESCRIPTION)
        assertThat(task.isCompleted()).isTrue()
    }

    @Test
    fun `complete runbook`() {
        // Given
        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(RUNBOOK_NAME, OWNER_ID))
        val taskId = runbookApplicationService.addTask(AddTask(runbookId, TASK_NAME, TASK_DESCRIPTION, TASK_ASSIGNEE_ID))
        runbookApplicationService.startTask(StartTask(runbookId, taskId, TASK_ASSIGNEE_ID))
        runbookApplicationService.completeTask(CompleteTask(runbookId, taskId, TASK_ASSIGNEE_ID))

        // When
        runbookApplicationService.completeRunbook(CompleteRunbook(runbookId, OWNER_ID))

        // Then
        val runbook = runbookRepository.findById(runbookId).get()
        assertThat(runbook.isCompleted()).isTrue()
    }

}