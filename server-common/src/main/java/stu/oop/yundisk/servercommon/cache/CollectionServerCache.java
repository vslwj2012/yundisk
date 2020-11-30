package stu.oop.yundisk.servercommon.cache;

public interface CollectionServerCache<E> extends ServerCache<E> {
    void add(E value);
    void del(E value);
    void update(E value);
}
