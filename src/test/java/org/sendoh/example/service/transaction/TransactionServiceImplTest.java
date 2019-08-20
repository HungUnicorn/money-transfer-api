package org.sendoh.example.service.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.model.Transaction;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    TransactionDao transactionDao;


    @Test
    public void shouldFind() {
        // given
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        final Transaction transaction = Transaction.fromTransferRequest(transferRequest);

        when(transactionDao.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionDao);

        assertEquals(transaction, transactionService.findById(transaction.getId()).orElse(null));
    }

    @Test
    public void shouldNotFind() {
        when(transactionDao.findById(any())).thenReturn(Optional.empty());

        TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionDao);

        assertFalse(transactionService.findById(UUID.fromString("a-a-a-a-1")).isPresent());
    }
}
