package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.Feed;
import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by solusi247 on 22/12/15.
 */
public class FeedRepository extends ModelRepository<Feed> {
    public FeedRepository() {
        super("feed", Feed.class);
    }
}
