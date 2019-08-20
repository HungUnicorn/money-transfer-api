package org.sendoh.example.service.transaction;

import org.sendoh.example.model.Transaction;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Provide transaction operations
 *
 */

public interface TransactionService {

    Optional<Transaction> findById(UUID transactionId);
}
