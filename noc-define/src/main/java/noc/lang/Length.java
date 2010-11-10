package noc.lang;

import noc.frame.ComparableScala;

public interface Length extends ComparableScala<Integer> {
    Length minus(Length value);
}
