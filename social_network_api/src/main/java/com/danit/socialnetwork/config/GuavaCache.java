package com.danit.socialnetwork.config;


import com.danit.socialnetwork.model.DbUser;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Data
public class GuavaCache {
  private static Cache<String, Integer> activateCodeCache = CacheBuilder.newBuilder()
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

  private static Cache<String, List<DbUser>> userCache = CacheBuilder.newBuilder()
      .maximumSize(1000)
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .build();

  public static Integer putActivateCodeCache(String key, Integer value) {
    activateCodeCache.put(key, value);
    return value;
  }

  public static Integer getUncheckedActivateCodeCache(String key) {
    return activateCodeCache.getIfPresent(key);
  }

  public static List<DbUser> putUserCache(String key, List<DbUser> cachedUsers) {
    userCache.put(key, cachedUsers);
    return cachedUsers;
  }

  public static List<DbUser> getUserCache(String key) {
    return userCache.getIfPresent(key);
  }
}




