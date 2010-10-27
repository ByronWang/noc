package noc.lang;

import noc.frame.ComparableScala;

public interface Minute extends ComparableScala<Integer> {
	Minute minus(Minute value);
}
