package com.paucls.runbookDDD.api.runbook

import com.paucls.runbookDDD.application.runbook.RunbookCommand
import com.paucls.runbookDDD.application.runbook.RunbookCommand.CreateRunbook
import com.paucls.runbookDDD.application.runbook.RunbookService
import com.paucls.runbookDDD.domain.model.runbook.Runbook
import com.paucls.runbookDDD.domain.model.runbook.Task
import com.paucls.runbookDDD.infrastructure.auth.AuthenticationFacade
import com.paucls.runbookDDD.infrastructure.persistence.RunbookRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class RunbookController(
        val authenticationFacade: AuthenticationFacade,
        val runbookApplicationService: RunbookService,
        val runbookRepository: RunbookRepository
) {

    /**
     * Commands
     */

    @RequestMapping(value = ["/runbooks"], method = [(RequestMethod.POST)])
    fun createRunbook(@RequestBody runbookDto: RunbookDto): ResponseEntity<RunbookDto> {
        val userId = authenticationFacade.getCurrentUserId()

        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(runbookDto.name, userId))

        val runbook = runbookRepository.findById(runbookId).get()

        return ResponseEntity(mapToRunbookDto(runbook), HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/runbooks/{runbookId}/tasks"], method = [(RequestMethod.POST)])
    fun addTask(@PathVariable runbookId: String, @RequestBody taskDto: TaskDto): ResponseEntity<TaskDto> {
        val userId = authenticationFacade.getCurrentUserId()

        val taskId = runbookApplicationService.addTask(RunbookCommand.AddTask(
                runbookId = runbookId,
                name = taskDto.name,
                description = taskDto.description,
                assigneeId = taskDto.assigneeId,
                userId = userId))

        val runbook = runbookRepository.findById(runbookId).get()
        val newTask = runbook.tasks[taskId] ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(mapToTaskDto(newTask), HttpStatus.CREATED)
    }


    /**
     * Queries
     */

    @RequestMapping(value = ["/runbooks"], method = [(RequestMethod.GET)])
    fun getRunbooks(): ResponseEntity<List<RunbookDto>> {
        val runbooks = runbookRepository.findAll().map(this::mapToRunbookDto)

        return ResponseEntity(runbooks, HttpStatus.OK)
    }

    private fun mapToRunbookDto(runbook: Runbook): RunbookDto {
        return RunbookDto(
                runbookId = runbook.runbookId,
                name = runbook.name,
                ownerId = runbook.ownerId,
                isCompleted = runbook.isCompleted())
    }

    private fun mapToTaskDto(task: Task): TaskDto {
        return TaskDto(
                taskId = task.taskId,
                name = task.name,
                description = task.description,
                assigneeId = task.assigneeId)
    }
}