package org.sendoh.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.dto.response.TransferResponse;
import org.sendoh.example.service.transfer.TransferService;

import javax.inject.Inject;
import javax.validation.ValidationException;

@Controller("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @Inject
    public TransferController(final TransferService transactionService) {
        this.transferService = transactionService;
    }

    @Post
    @Consumes
    @Produces
    public HttpResponse postTransfer(@Body final TransferRequest transferRequest) {
        try {
            final TransferResponse response = transferService.transfer(transferRequest);
            return HttpResponse.ok(response);
        } catch (ValidationException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }
}
