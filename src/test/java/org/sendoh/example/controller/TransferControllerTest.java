package org.sendoh.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.service.transfer.TransferService;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @Test
    public void shouldPostTransfer() {
        final TransferController transferController = new TransferController(transferService);

        // given
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        when(transferService.transfer(any())).thenReturn(any());

        final HttpResponse httpResponse = transferController.postTransfer(transferRequest);

        assertEquals(HttpStatus.OK, httpResponse.status());
    }

    @Test
    public void shouldNotPostTransfer() {
        when(transferService.transfer(any())).thenThrow(new ValidationException("Invalid"));


        final TransferController transferController = new TransferController(transferService);

        // given
        final TransferRequest transferRequest =
                new TransferRequest("aaaaa", UUID.fromString("a-a-a-a-1"),
                        UUID.fromString("b-b-b-b-2"),
                        new BigDecimal(30),
                        Currency.getInstance(Locale.TAIWAN), "");

        final HttpResponse httpResponse = transferController.postTransfer(transferRequest);

        assertEquals(HttpStatus.BAD_REQUEST, httpResponse.status());
    }
}
