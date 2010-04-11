package noc.lang;

import noc.frame.Scala;

public interface Bool extends Scala<java.lang.Boolean> {
	Bool and(boolean compress);
	Bool and(Bool compress);
	Bool or(boolean compress);
	Bool or(Bool compress);
	Bool not();	
}
