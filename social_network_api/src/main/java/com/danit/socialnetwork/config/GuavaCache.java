package com.danit.socialnetwork.config;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
@Data
public class GuavaCache {
  private static Cache<String, Integer> cache = CacheBuilder.newBuilder()
      .initialCapacity(32)
      .concurrencyLevel(8)
      .removalListener(new RemovalListener<String, Integer>() {
        @Override
        public void onRemoval(RemovalNotification<String, Integer> notification) {
          log.info(String.format("Removed entry: %s -> %s", notification.getKey(), notification.getValue()));
          log.info(String.format("Cause: %s", notification.getCause()));
        }
      })
      .maximumSize(20)
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .build();

  public void put(String key, Integer value) {
    cache.put(key, value);
  }

  public Integer getUnchecked(String key) {
    return cache.getIfPresent(key);
  }

}




