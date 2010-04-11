package noc.lang;

import noc.frame.ComparableScala;

public interface Height extends ComparableScala<Integer> {
	Height minus(Height value);
}
