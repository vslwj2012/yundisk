package stu.oop.yundisk.servercommon.cache;

import java.util.Map;

public interface MapServerCache<K,V> extends ServerCache {
    Map<K,V> getResult();
    void add(K key,V value);
}
