package noc.frame.lang;

import noc.frame.lang.annotation.Abstract;

@Abstract
public interface Scala<O>{
	void set(O value);	
	O ordinal();
}
