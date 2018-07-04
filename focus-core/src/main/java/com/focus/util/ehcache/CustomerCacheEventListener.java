package com.focus.util.ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class CustomerCacheEventListener implements CacheEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache removed. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache put. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache updated. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    public void notifyElementExpired(Ehcache ehcache, Element element) {
        logger.info("cache expired. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        logger.info("cache evicted. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    public void notifyRemoveAll(Ehcache ehcache) {
        logger.info("all elements removed. cache name = {}", ehcache.getName());
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void dispose() {
        logger.info("cache dispose.");
    }
}