package org.sendoh.example.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Hold transaction information per account
 */
@ToString
@EqualsAndHashCode
public final class AccountTransaction {

    private final UUID id;
    private final UUID accountId;
    private final BigDecimal amount;

    public AccountTransaction(final UUID accountId, final BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
