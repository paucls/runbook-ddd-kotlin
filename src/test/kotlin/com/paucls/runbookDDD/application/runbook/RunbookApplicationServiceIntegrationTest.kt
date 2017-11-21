package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.persistence.RunbookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class RunbookApplicationServiceIntegrationTest {

    private val RUNBOOK_NAME = "runbook-name"
    private val OWNER_ID = "owner-id"

    @Autowired
    private lateinit var runbookRepository: RunbookRepository

    @Autowired
    private lateinit var runbookApplicationService: RunbookApplicationService

    @Before
    fun setup() {
        runbookRepository.deleteAll()
    }

    @Test
    fun `create runbook`() {
        val runbookId = runbookApplicationService.createRunbook(
                CreateRunbook(RUNBOOK_NAME, OWNER_ID))

        val newRunbook = runbookRepository.findById(runbookId).get()

        assertThat(newRunbook).isNotNull()
        assertThat(newRunbook.name).isEqualTo(RUNBOOK_NAME)
        assertThat(newRunbook.ownerId).isEqualTo(OWNER_ID)
    }

}