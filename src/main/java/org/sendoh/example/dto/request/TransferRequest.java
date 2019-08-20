package org.sendoh.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;


/**
 * Receive the transfer request from client. requestId is considered unique.
 *
 * ref: https://revolutdev.github.io/business-api/#transfers
 *
 */

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransferRequest {
    private String requestId;

    private UUID sourceAccountId;

    private UUID targetAccountId;

    private BigDecimal amount;

    private Currency currency;

    private String reference;
}
