package org.sendoh.example.service.transaction;

import lombok.Getter;
import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.model.Transaction;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;


@Getter
@Singleton
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Transaction> findById(UUID transactionId) {
        return transactionDao.findById(transactionId);
    }
}
