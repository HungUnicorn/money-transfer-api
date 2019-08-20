package org.sendoh.example.dao.transfer;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

/**
 * In-memory caching unique requestId
 *
 * */
@Singleton
public class InMomoryTransferRequestIdDao implements TransferRequestIdDao {
    private static final Set<String> values = new HashSet<>();

    @Override
    public void add(String value) {
        values.add(value);

    }

    @Override
    public boolean contains(String value) {
        return values.contains(value);
    }
}
