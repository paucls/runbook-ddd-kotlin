package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.domain.model.runbook.RunbookAggregate
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

        val runbook = RunbookAggregate(runbookId, c.name, c.ownerId)
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
}