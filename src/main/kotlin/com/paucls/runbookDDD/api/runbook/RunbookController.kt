package com.paucls.runbookDDD.api.runbook

import com.paucls.runbookDDD.application.runbook.CreateRunbook
import com.paucls.runbookDDD.application.runbook.RunbookApplicationService
import com.paucls.runbookDDD.persistence.RunbookRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class RunbookController(
        val runbookApplicationService: RunbookApplicationService,
        val runbookRepository: RunbookRepository
) {

    // Hardcoded current user, in a real app it will be come from auth module
    private val CURRENT_USER_ID = "user-id"

    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Runbook App!"
    }

    /**
     * Commands
     */

    @RequestMapping(value = "/runbooks", method = arrayOf(RequestMethod.POST))
    fun createRunbook(@RequestBody runbookDto: RunbookDto): ResponseEntity<RunbookDto> {
        val runbookId = runbookApplicationService.createRunbook(
                CreateRunbook(runbookDto.name, CURRENT_USER_ID))

        // TODO: Should the Service return the Entity? Should a DTO be used for the API layer or the Entity?
        val runbook = runbookRepository.findById(runbookId).get()

        return ResponseEntity<RunbookDto>(
                RunbookDto(runbook.runbookId, runbook.name, runbook.ownerId),
                HttpStatus.CREATED)
    }

}