package org.sendoh.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sendoh.example.model.TransactionState;

import java.time.Instant;
import java.util.UUID;

/**
 * Response for client calling transfer
 *
 * ref: https://revolutdev.github.io/business-api/#transfers
 * */

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private UUID transactionId;
    private TransactionState state;
    private Instant createdAt;
    private Instant completedAt;
}
