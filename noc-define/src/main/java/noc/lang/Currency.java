package noc.lang;

import java.math.BigDecimal;

import noc.frame.ComparableScala;

public interface Currency extends ComparableScala<BigDecimal> {
    Currency plus(Currency value);

    Currency minus(Currency value);

    Currency multiply(Count value);

    Currency mod(Count value);
}
