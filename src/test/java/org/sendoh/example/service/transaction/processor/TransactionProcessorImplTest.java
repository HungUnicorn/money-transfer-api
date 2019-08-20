package org.sendoh.example.service.transaction.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.dto.response.TransactionAccountResponse;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.model.TransactionState;
import org.sendoh.example.service.transaction.account.TransactionAccountClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionProcessorImplTest {

    @Mock
    TransactionDao transactionDao;

    @Mock
    TransactionAccountClient transactionAccountService;

    @Test
    public void shouldUpdate_givenPendingTransaction() {
        final TransactionProcessorImpl transactionProcessor =
                new TransactionProcessorImpl(transactionDao, transactionAccountService);

        // given
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        final Transaction transaction = Transaction.fromTransferRequest(transferRequest);

        final TransactionAccountResponse response = new TransactionAccountResponse("Test",
                TransactionState.PENDING, Instant.now());

        when(transactionAccountService.request(transaction)).thenReturn(response);

        transactionProcessor.execute(transaction);

        verify(transactionDao, times(1))
                .updateIfExists(any());
    }

    @Test
    public void shouldNotUpdateAndRequest_givenNotPendingTransaction() {
        final TransactionProcessorImpl transactionProcessor =
                new TransactionProcessorImpl(transactionDao, transactionAccountService);

        // given
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        final Transaction transaction = Transaction.fromTransferRequest(transferRequest);

        final TransactionAccountResponse response = new TransactionAccountResponse("Test",
                TransactionState.COMPLETED, Instant.now());

        final Transaction finishedTransaction = Transaction.fromTransactionResponse(transaction, response);

        transactionProcessor.execute(finishedTransaction);

        verify(transactionDao, never()).updateIfExists(any());
        verify(transactionAccountService, never()).request(any());
    }
}
