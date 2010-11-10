package noc.annotation;

import noc.frame.Scala;

public @interface WireType {
    Class<? extends Scala<?>> value();
}
