package org.sendoh.example.validator;

import org.sendoh.example.dao.transfer.TransferRequestIdDao;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.validator.TransferRequestValidatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferRequestValidatorImplTest {

    @Mock
    TransferRequestIdDao transferRequestIdDao;

    @Test (expected = ValidationException.class)
    public void shouldThrowValidationException_givenSameAccount() {
        TransferRequestValidatorImpl validator = new TransferRequestValidatorImpl(transferRequestIdDao);

        UUID sameAccount = UUID.fromString("a-a-a-a-1");

        TransferRequest transferRequest = new TransferRequest("123", sameAccount,
                sameAccount, new BigDecimal(3), Currency.getInstance(Locale.TAIWAN), "test");

        validator.validate(transferRequest);
    }

    @Test (expected = ValidationException.class)
    public void shouldThrowValidationException_givenNegativeValue() {
        TransferRequestValidatorImpl validator = new TransferRequestValidatorImpl(transferRequestIdDao);


        TransferRequest transferRequest = new TransferRequest("123", UUID.fromString("a-a-a-a-1"),
                UUID.fromString("a-a-a-a-5"), new BigDecimal(-3),
                Currency.getInstance(Locale.TAIWAN), "test");
        validator.validate(transferRequest);
    }

    @Test (expected = ValidationException.class)
    public void shouldThrowValidationException_givenRepeatedRequestId() {
        when(transferRequestIdDao.contains(any())).thenReturn(true);

        TransferRequestValidatorImpl validator = new TransferRequestValidatorImpl(transferRequestIdDao);

        TransferRequest transferRequest = new TransferRequest("123", UUID.fromString("a-a-a-a-1"),
                UUID.fromString("a-a-a-a-5"), new BigDecimal(3),
                Currency.getInstance(Locale.TAIWAN), "test");

        validator.validate(transferRequest);
    }

    @Test
    public void shouldValidateWithoutException() {
        when(transferRequestIdDao.contains(any())).thenReturn(false);
        doNothing().when(transferRequestIdDao).add(any());

        TransferRequestValidatorImpl validator = new TransferRequestValidatorImpl(transferRequestIdDao);

        TransferRequest transferRequest = new TransferRequest("123", UUID.fromString("a-a-a-a-1"),
                UUID.fromString("a-a-a-a-5"), new BigDecimal(3),
                Currency.getInstance(Locale.TAIWAN), "test");

        validator.validate(transferRequest);
    }
}