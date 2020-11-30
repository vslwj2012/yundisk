package stu.oop.yundisk.servercommon.cache;

import java.util.Set;

public interface SetServerCache<E> extends CollectionServerCache<E> {
    Set<E> getResult();
}
