package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.application.runbook.RunbookCommand.*
import com.paucls.runbookDDD.domain.model.runbook.Runbook
import com.paucls.runbookDDD.persistence.RunbookRepository
import com.paucls.runbookDDD.persistence.TaskRepository
import com.paucls.runbookDDD.persistence.nextIdentity
import org.springframework.stereotype.Service

@Service
class RunbookApplicationService(
        val runbookRepository: RunbookRepository,
        val taskRepository: TaskRepository
) {

    fun createRunbook(c: CreateRunbook): String {
        val runbookId = runbookRepository.nextIdentity()

        val runbook = Runbook(runbookId, c.name, c.ownerId)

        runbookRepository.save(runbook)
        return runbookId
    }

    fun addTask(c: AddTask): String {
        val runbook = runbookRepository.findById(c.runbookId).get()
        val taskId = taskRepository.nextIdentity()

        runbook.addTask(taskId, c.name, c.description, c.assigneeId)

        runbookRepository.save(runbook)
        return taskId
    }

    fun startTask(c: StartTask) {
        val runbook = runbookRepository.findById(c.runbookId).get()

        runbook.startTask(c.taskId, c.userId)

        runbookRepository.save(runbook)
    }

    fun completeTask(c: CompleteTask) {
        val runbook = runbookRepository.findById(c.runbookId).get()

        runbook.completeTask(c.taskId, c.userId)

        runbookRepository.save(runbook)
    }

    fun completeRunbook(c: CompleteRunbook) {
        val runbook = runbookRepository.findById(c.runbookId).get()

        runbook.completeRunbook(c.userId)

        runbookRepository.save(runbook)
    }
}