package org.sendoh.example.validator;

import org.sendoh.example.dao.transfer.TransferRequestIdDao;
import org.sendoh.example.dto.request.TransferRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Validate transfer request by:
 * - duplicate request id
 * - same accounts
 * - non-positive amount
 *
 * */

@Singleton
public class TransferRequestValidatorImpl implements TransferRequestValidator {

    private final TransferRequestIdDao transferRequestIdDao;

    @Inject
    public TransferRequestValidatorImpl(TransferRequestIdDao transferRequestIdDao) {
        this.transferRequestIdDao = transferRequestIdDao;
    }

    @Override
    public void validate(TransferRequest transferRequest) throws ValidationException {

        if (isSameAccount(transferRequest.getSourceAccountId(), transferRequest.getTargetAccountId())) {
            throw new ValidationException("Invalid transfer account: " + transferRequest.toString());
        }

        if (!isPositiveValue(transferRequest.getAmount())) {
            throw new ValidationException("Invalid transfer amount: " + transferRequest.toString());
        }

        handleDuplicateRequestId(transferRequest);

    }

    private void handleDuplicateRequestId(TransferRequest transferRequest) {
        if (transferRequestIdDao.contains(transferRequest.getRequestId())) {
            throw new ValidationException("Already existed");
        } else {
            transferRequestIdDao.add(transferRequest.getRequestId());
        }
    }

    private boolean isSameAccount(UUID sourceAccountId, UUID targetAccountId) {
        return Objects.equals(sourceAccountId, targetAccountId);
    }

    private boolean isPositiveValue(BigDecimal value) {
        return (value.compareTo(BigDecimal.ZERO) > 0);
    }
}
