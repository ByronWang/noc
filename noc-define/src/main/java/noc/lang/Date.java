package noc.lang;

import noc.frame.ComparableScala;

public interface Date extends ComparableScala<java.util.Date> {
	Number endureYear();

	Number endureMonth();

	Number endureDay();
}
