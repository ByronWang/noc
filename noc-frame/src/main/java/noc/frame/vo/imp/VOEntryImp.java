package noc.frame.vo.imp;

import noc.frame.vo.V;
import noc.frame.vo.VOEntry;

public class VOEntryImp implements VOEntry {
    String key;
    V value;

    public VOEntryImp(String key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public void setValue(V v) {
        this.value = v;
    }

}
