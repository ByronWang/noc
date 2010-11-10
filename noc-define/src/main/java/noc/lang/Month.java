package noc.lang;

import noc.frame.ComparableScala;

public interface Month extends ComparableScala<Integer> {
    Month minus(Month value);
}
