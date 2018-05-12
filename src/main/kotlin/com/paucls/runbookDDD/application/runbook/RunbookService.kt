package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.application.runbook.RunbookCommand.AddTask
import com.paucls.runbookDDD.application.runbook.RunbookCommand.CompleteRunbook
import com.paucls.runbookDDD.application.runbook.RunbookCommand.CompleteTask
import com.paucls.runbookDDD.application.runbook.RunbookCommand.CreateRunbook
import com.paucls.runbookDDD.application.runbook.RunbookCommand.StartTask
import com.paucls.runbookDDD.domain.model.runbook.Runbook
import com.paucls.runbookDDD.infrastructure.persistence.RunbookRepository
import com.paucls.runbookDDD.infrastructure.persistence.nextIdentity
import org.springframework.stereotype.Service

@Service
class RunbookService(val runbookRepository: RunbookRepository) {

    fun createRunbook(c: CreateRunbook): String {
        val runbookId = runbookRepository.nextIdentity()

        val runbook = Runbook(runbookId, c.name, c.userId)

        runbookRepository.save(runbook)
        return runbookId
    }

    fun addTask(c: AddTask): String {
        val runbook = runbookRepository.findById(c.runbookId).get()
        val taskId = runbookRepository.nextIdentity()

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