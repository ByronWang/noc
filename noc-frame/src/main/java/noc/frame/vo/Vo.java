package noc.frame.vo;

import noc.frame.Identifiable;

public interface Vo extends V ,Identifiable {
    void put(String name, Object value);

    Object get(String name);

    Object get(int i);

    int size();
}
