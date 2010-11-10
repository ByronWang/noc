package noc.frame;

import noc.annotation.Abstract;

@Abstract
public interface Scala<O> {
    void set(O value);

    O ordinal();
}
