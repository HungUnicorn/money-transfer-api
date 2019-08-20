package org.sendoh.example.service.transfer;

import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.dto.response.TransferResponse;

import javax.validation.ValidationException;

/**
 * Generate valid transaction from transfer. Return response for tracking the generated transaction
 *
 * If transfer is invalid, throw exception containing the reason
 *
 * */
public interface TransferService {

    TransferResponse transfer(TransferRequest transferRequest) throws ValidationException;
}
