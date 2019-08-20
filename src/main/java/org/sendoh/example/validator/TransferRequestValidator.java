package org.sendoh.example.validator;

import org.sendoh.example.dto.request.TransferRequest;

import javax.validation.ValidationException;

/**
 * Validate transfer request. If invalid, throw exception containing error message.
 *
 * */
public interface TransferRequestValidator {
    void validate(TransferRequest transferRequest) throws ValidationException;
}
