package org.sendoh.example.service.transaction.processor;

import lombok.extern.slf4j.Slf4j;
import org.sendoh.example.dao.transaction.TransactionDao;
import org.sendoh.example.dto.response.TransactionAccountResponse;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.model.TransactionState;
import org.sendoh.example.service.transaction.account.TransactionAccountClient;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Execute pending transactions by communicating with other services, and save the result to Dao
 */

@Singleton
@Slf4j
public class TransactionProcessorImpl implements TransactionProcessor {

    private final TransactionDao transactionDao;
    private final TransactionAccountClient accountClient;

    @Inject
    public TransactionProcessorImpl(final TransactionDao transactionDao,
                                    final TransactionAccountClient transactionAccountService) {
        this.transactionDao = transactionDao;
        this.accountClient = transactionAccountService;
    }

    public void execute(Transaction transaction) {
        if (transaction.getState().equals(TransactionState.PENDING)) {
            log.info("Updating transaction: {}", transaction.toString());
            final TransactionAccountResponse transactionAccountResponse = accountClient.request(transaction);

            final Transaction fromTransactionResponse = Transaction
                    .fromTransactionResponse(transaction, transactionAccountResponse);

            transactionDao.updateIfExists(fromTransactionResponse);
            log.info("Transaction Updated: {}", fromTransactionResponse.toString());
        }
    }
}
