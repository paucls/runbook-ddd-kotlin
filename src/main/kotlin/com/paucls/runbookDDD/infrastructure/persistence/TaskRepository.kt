package com.paucls.runbookDDD.infrastructure.persistence

import com.paucls.runbookDDD.domain.model.runbook.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskRepository : CrudRepository<Task, String>

fun TaskRepository.nextIdentity(): String {
    return UUID.randomUUID().toString()
}