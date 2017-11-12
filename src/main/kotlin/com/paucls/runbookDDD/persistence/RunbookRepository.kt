package com.paucls.runbookDDD.persistence;

import com.paucls.runbookDDD.domain.model.runbook.RunbookAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*

interface RunbookRepository : JpaRepository<RunbookAggregate, String>

fun RunbookRepository.nextIdentity(): String {
    return UUID.randomUUID().toString()
}
