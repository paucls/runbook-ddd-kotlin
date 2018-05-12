package com.paucls.runbookDDD.application.runbook

import com.paucls.runbookDDD.domain.model.runbook.TaskAdded
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

/**
 * Demo event handler to test how Spring Data AbstractAggregateRoot entities dispatch events.
 *
 * If we are interested in publishing certain domain events outside this microservice we could
 * create a Relay service to publish them to message queues.
 */
@Service
class HandleTaskAdded {

    @TransactionalEventListener
    fun handle(event: TaskAdded) {
        println(">> Handling Runbook Task Added: $event")

        // TODO: Call an App Service use case interested in this domain event
    }

}
