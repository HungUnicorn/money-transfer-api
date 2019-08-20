package org.sendoh.example.dao.transfer;

/**
 * Provide caching to store unique requestId
 * */
public interface TransferRequestIdDao {
    void add(String requestId);

    boolean contains(String requestId);
}
