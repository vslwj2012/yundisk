package stu.oop.yundisk.servercommon.cache;

import java.util.List;

public interface ListServerCache<E> extends CollectionServerCache<E> {
    List<E> getResult();
}
