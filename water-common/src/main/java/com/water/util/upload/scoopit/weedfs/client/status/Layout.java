package com.water.util.upload.scoopit.weedfs.client.status;

import java.util.List;

import com.water.util.upload.scoopit.weedfs.client.ReplicationStrategy;

public class Layout {
    public String collection;
    public String replication;
    public List<Integer> writables;

    public ReplicationStrategy getReplicationStrategy() {
        return ReplicationStrategy.fromParameterValue(replication);
    }
}
