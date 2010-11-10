package noc.frame.vo;

public interface VOEntry {
    String getKey();

    V getValue();

    void setValue(V v);
}
