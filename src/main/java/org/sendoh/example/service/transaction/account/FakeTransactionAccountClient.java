package org.sendoh.example.service.transaction.account;

import org.sendoh.example.model.TransactionState;
import org.sendoh.example.model.Transaction;
import org.sendoh.example.dto.response.TransactionAccountResponse;

import javax.inject.Singleton;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


/**
 * Unreal implementation account service client
 *
 * */
@Singleton
public class FakeTransactionAccountClient implements TransactionAccountClient {
    @Override
    public TransactionAccountResponse request(Transaction transaction) {

        try {
            TimeUnit.SECONDS.sleep(2);
            return new TransactionAccountResponse(null, TransactionState.COMPLETED, Instant.now());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new TransactionAccountResponse("Cannot complete transaction",
                    TransactionState.FAILED, Instant.now());
        }
    }
}
