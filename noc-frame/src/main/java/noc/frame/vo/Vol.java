package noc.frame.vo;

public interface Vol extends V, Iterable<Vo> {
    void add(Vo vo);

    Vo get(int index);

    Vo get(String keys);

    void put(Vo vo);

    int size();

    Vol lookup(String cause);
}
