package noc.lang;

import noc.frame.ComparableScala;

public interface Count extends ComparableScala<Integer> {
    Count plus(Count value);
}
