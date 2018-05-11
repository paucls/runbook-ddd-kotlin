package com.paucls.runbookDDD.persistence

import com.paucls.runbookDDD.domain.model.runbook.Runbook
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RunbookRepository : CrudRepository<Runbook, String>

fun RunbookRepository.nextIdentity(): String {
    return UUID.randomUUID().toString()
}
