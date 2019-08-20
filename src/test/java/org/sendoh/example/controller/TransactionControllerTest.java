package org.sendoh.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.service.transaction.TransactionService;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    TransactionService transactionService;

    @Test
    public void shouldFindTransactionById() {
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        final Transaction transaction = Transaction.fromTransferRequest(transferRequest);
        when(transactionService.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        TransactionController transactionController = new TransactionController(transactionService);

        final HttpResponse<Transaction> transactionById = transactionController
                .getTransactionById(transaction.getId());

        assertEquals(HttpStatus.OK, transactionById.status());
        assertEquals(transaction, transactionById.body());
    }

    @Test
    public void shouldNotFindTransactionById() {
        final UUID uuid = UUID.fromString("a-a-a-a-5");

        when(transactionService.findById(uuid)).thenReturn(Optional.empty());

        TransactionController transactionController = new TransactionController(transactionService);

        final HttpResponse<Transaction> transactionById = transactionController
                .getTransactionById(uuid);

        assertEquals(HttpStatus.NOT_FOUND, transactionById.status());
    }
}
