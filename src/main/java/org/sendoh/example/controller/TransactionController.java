package org.sendoh.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.service.transaction.TransactionService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Controller("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Inject
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Get("/{transactionId}")
    @Produces
    public HttpResponse<Transaction> getTransactionById(final UUID transactionId) {
        final Optional<Transaction> transaction = transactionService.findById(transactionId);

        if (transaction.isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(transaction.get());
    }
}
