package noc.frame.lang.annotation;

import noc.frame.lang.Scala;

public @interface WireType {
	Class<? extends Scala<?>> value();
}
