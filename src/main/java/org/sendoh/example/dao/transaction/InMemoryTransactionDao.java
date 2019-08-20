package org.sendoh.example.dao.transaction;

import org.sendoh.example.model.Transaction;
import org.sendoh.example.model.TransactionState;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * Provide in-memory operations on transaction storage.
 * <p>
 * Duplicate transaction operation is ignored via putIfAbsent and replace.
 */
@Singleton
public class InMemoryTransactionDao implements TransactionDao {

    private final ConcurrentMap<UUID, Transaction> storage = new ConcurrentHashMap<>();

    @Override
    public Stream<Transaction> pendingStream() {
        return storage.values().stream()
                .filter(it -> it.getState().equals(TransactionState.PENDING));
    }

    @Override
    public void insertIfNotExists(final Transaction transaction) {
        final UUID transactionId = transaction.getId();
        storage.putIfAbsent(transactionId, transaction);
    }

    @Override
    public void updateIfExists(final Transaction transaction) {
        final UUID transactionId = transaction.getId();
        storage.replace(transactionId, transaction);
    }

    @Override
    public Optional<Transaction> findById(final UUID transactionId) {
        return Optional.ofNullable(storage.get(transactionId));
    }
}
