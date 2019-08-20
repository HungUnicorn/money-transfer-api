package org.sendoh.example.service.transfer;

import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.dto.request.TransferRequest;
import org.sendoh.example.dto.response.TransferResponse;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.model.TransactionState;
import org.sendoh.example.validator.TransferRequestValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ValidationException;

/**
 * Validate transfer request. If valid, store it in transaction storage
 *
 * */

@Singleton
public class TransferServiceImpl implements TransferService {

    private final TransactionDao transactionDao;
    private final TransferRequestValidator transferRequestValidator;

    @Inject
    public TransferServiceImpl(final TransactionDao transactionDao,
                               final TransferRequestValidator transferRequestValidator) {
        this.transactionDao = transactionDao;
        this.transferRequestValidator = transferRequestValidator;
    }

    @Override
    public TransferResponse transfer(TransferRequest transferRequest) throws ValidationException {
        transferRequestValidator.validate(transferRequest);

        final Transaction transaction = Transaction.fromTransferRequest(transferRequest);

        transactionDao.insertIfNotExists(transaction);

        return new TransferResponse(transaction.getId(), TransactionState.PENDING, transaction.getCreatedAt(),
                transaction.getCompletedAt());
    }
}
