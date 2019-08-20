package org.sendoh.example.dao.transaction;

import org.sendoh.example.model.Transaction;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Provide operations for transaction storage. Should also provide pending transactions as stream
 * that should be processed.
 *
 * Ignore duplicate transactions' inserts and updates.
 *
 * */

public interface TransactionDao {

    Stream<Transaction> pendingStream();

    Optional<Transaction> findById(UUID transactionId);

    void insertIfNotExists(Transaction transaction);

    void updateIfExists(Transaction transaction);
}
