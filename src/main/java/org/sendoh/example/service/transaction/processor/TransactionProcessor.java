package org.sendoh.example.service.transaction.processor;

import org.sendoh.example.model.Transaction;

/**
 * Process transaction. Transaction is processed with other services, including account API and transaction storage.
 *
 * **/

public interface TransactionProcessor {
    void execute(Transaction transaction);
}
