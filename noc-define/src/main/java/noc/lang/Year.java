package noc.lang;

import noc.frame.ComparableScala;

public interface Year extends ComparableScala<Integer> {
	Year minus(Year value);
}
