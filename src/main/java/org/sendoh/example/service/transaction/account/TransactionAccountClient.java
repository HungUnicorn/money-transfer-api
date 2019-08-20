package org.sendoh.example.service.transaction.account;

import org.sendoh.example.dto.response.TransactionAccountResponse;
import org.sendoh.example.model.Transaction;

/**
 * Request transaction response by communicating with other account services,
 * and decide transaction response based on the response.
 *
 * {@link org.sendoh.example.model.TransactionState}
 * PENDING: if a transaction is processing and the account is locked
 * COMPLETED: finish without reason code
 * FAILED: account is blocked and cannot withdraw or deposit
 *
 * */
public interface TransactionAccountClient {

    TransactionAccountResponse request(Transaction transaction);
}
