package com.paucls.runbookDDD.api.runbook

import com.paucls.runbookDDD.application.runbook.RunbookService
import com.paucls.runbookDDD.application.runbook.RunbookCommand.CreateRunbook
import com.paucls.runbookDDD.domain.model.runbook.Runbook
import com.paucls.runbookDDD.persistence.RunbookRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class RunbookController(
        val runbookApplicationService: RunbookService,
        val runbookRepository: RunbookRepository
) {

    // Hardcoded current user id, in a real app it will be come from auth module
    private val CURRENT_USER_ID = "user-id"

    /**
     * Commands
     */

    @RequestMapping(value = ["/runbooks"], method = [(RequestMethod.POST)])
    fun createRunbook(@RequestBody runbookDto: RunbookDto): ResponseEntity<RunbookDto> {
        val runbookId = runbookApplicationService.createRunbook(CreateRunbook(runbookDto.name, CURRENT_USER_ID))

        val runbook = runbookRepository.findById(runbookId).get()

        return ResponseEntity(mapToDto(runbook), HttpStatus.CREATED)
    }

    /**
     * Queries
     */

    @RequestMapping(value = ["/runbooks"], method = [(RequestMethod.GET)])
    fun getRunbooks(): ResponseEntity<List<RunbookDto>> {
        val runbooks = runbookRepository.findAll().map(this::mapToDto)

        return ResponseEntity(runbooks, HttpStatus.OK)
    }

    private fun mapToDto(runbook: Runbook): RunbookDto {
        return RunbookDto(runbook.runbookId, runbook.name, runbook.ownerId, runbook.isCompleted())
    }

}