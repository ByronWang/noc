package noc.frame;

import java.util.List;

public interface Store<K, V> {// extends List<T> {
    void open();

    void close();

    V getReadonly(K key);

    V getForUpdate(K key);

    V update(K key, V v);

    List<V> list();
}
