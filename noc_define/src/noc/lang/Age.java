package noc.lang;

import noc.frame.ComparableScala;

public interface Age extends ComparableScala<Integer> {
	Age minus(Age value);
}
