package org.sendoh.example.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.sendoh.example.model.TransactionState;

import java.time.Instant;

/**
 * Response from account client
 *
 * */

@Getter
@EqualsAndHashCode
public class TransactionAccountResponse {
    private final String reasonCode;
    private final TransactionState transactionState;
    private final Instant completedAt;

    public TransactionAccountResponse(String reasonCode, TransactionState transactionState, Instant completedAt) {
        this.reasonCode = reasonCode;
        this.transactionState = transactionState;
        this.completedAt = completedAt;
    }
}
