package com.paucls.runbookDDD.persistence

import com.paucls.runbookDDD.domain.model.runbook.Task
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TaskRepository : JpaRepository<Task, String>

fun TaskRepository.nextIdentity(): String {
    return UUID.randomUUID().toString()
}