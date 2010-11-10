package noc.lang;

import noc.frame.ComparableScala;

public interface NumberValue extends ComparableScala<Integer> {
    NumberValue plus(NumberValue value);

    NumberValue minus(NumberValue value);

    NumberValue multiply(NumberValue value);

    NumberValue mod(NumberValue value);

    NumberValue multiply(Percent value);
}