package noc.lang;

import noc.frame.ComparableScala;

public interface Time extends ComparableScala<java.util.Date> {
	Hour endureHour();

	Minute endureMinute();

	Second endureSecond();

	Time plus(Time value);
}
