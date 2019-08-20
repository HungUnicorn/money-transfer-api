package org.sendoh.example.service.transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.validator.TransferRequestValidator;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceImplTest {

    @Mock
    TransactionDao transactionDao;

    @Mock
    TransferRequestValidator validator;

    @Test
    public void shouldTransfer() {
        doNothing().when(validator).validate(any());

        TransferServiceImpl transferService = new TransferServiceImpl(transactionDao,
                validator);

        TransferRequest transferRequest = new TransferRequest("123", UUID.fromString("a-a-a-a-1"),
                UUID.fromString("a-a-a-a-4"), new BigDecimal(3), Currency.getInstance(Locale.TAIWAN), "test");

        transferService.transfer(transferRequest);

        verify(transactionDao, times(1)).insertIfNotExists(any());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotTransfer_givenInvalidRequest() {
        doThrow(new ValidationException("test")).when(validator).validate(any());

        TransferServiceImpl transferService = new TransferServiceImpl(transactionDao,
                validator);

        TransferRequest transferRequest = new TransferRequest("123", UUID.fromString("a-a-a-a-1"),
                UUID.fromString("a-a-a-a-4"), new BigDecimal(3), Currency.getInstance(Locale.TAIWAN), "test");

        transferService.transfer(transferRequest);
    }
}
