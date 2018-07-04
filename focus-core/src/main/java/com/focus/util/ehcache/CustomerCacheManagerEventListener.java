package com.focus.util.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerCacheManagerEventListener implements CacheManagerEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final CacheManager cacheManager;

    public CustomerCacheManagerEventListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void init() throws CacheException {
        logger.info("init ehcache...");
    }

    
    public Status getStatus() {
        return null;
    }

    public void dispose() throws CacheException {
        logger.info("ehcache dispose...");
    }

    
    public void notifyCacheAdded(String s) {
        logger.info("cacheAdded... {}", s);
        logger.info(cacheManager.getCache(s).toString());
    }

    
    public void notifyCacheRemoved(String s) {

    }
}