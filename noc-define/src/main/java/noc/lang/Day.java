package noc.lang;

import noc.frame.ComparableScala;

public interface Day extends ComparableScala<Integer> {
    Day minus(Day value);
}
