package com.water.util.upload.scoopit.weedfs.client.catching;

import java.util.List;

import com.water.util.upload.scoopit.weedfs.client.Location;

public interface LookupCache {

    List<Location> lookup(long volumeId);

    void invalidate(long volumeId);

    void invalidate();

    void setLocation(long volumeId, List<Location> locations);
}
