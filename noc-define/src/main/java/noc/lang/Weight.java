package noc.lang;

import noc.frame.ComparableScala;

public interface Weight extends ComparableScala<Integer> {
    Weight minus(Weight value);
}
