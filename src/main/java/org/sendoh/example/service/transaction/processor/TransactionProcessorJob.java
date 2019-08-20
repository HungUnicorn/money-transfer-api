package org.sendoh.example.service.transaction.processor;

import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.sendoh.example.dao.transaction.TransactionDao;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * Execute transaction periodically. Note that ff schedule and process too aggressively,
 * duplicate processing wastes resource and is not stored
 * */

@Slf4j
public class TransactionProcessorJob {
    private final TransactionDao transactionDao;
    private final TransactionProcessor transactionProcessor;

    @Value("${transaction.execution.job.stream.limit:1}")
    private int streamLimit;

    @Inject
    public TransactionProcessorJob(TransactionDao transactionDao, TransactionProcessor transactionProcessor) {
        this.transactionDao = transactionDao;
        this.transactionProcessor = transactionProcessor;
    }

    @Scheduled(fixedRate = "${transaction.execution.job.rate:5s}", initialDelay = "5s")
    public void schedule() {
        log.info("Start processing transactions");

        transactionDao.pendingStream()
                .limit(streamLimit)
                .forEach(transaction -> CompletableFuture.runAsync(() -> transactionProcessor.execute(transaction)));
    }
}
