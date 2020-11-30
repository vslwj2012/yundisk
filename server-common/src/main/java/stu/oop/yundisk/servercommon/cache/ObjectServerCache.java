package stu.oop.yundisk.servercommon.cache;

public interface ObjectServerCache<E> extends ServerCache<E> {
    E getResult();
    void add(E value);
}
