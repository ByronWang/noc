package noc.lang;

import noc.frame.ComparableScala;

@Def.Unit("小时")
public interface Hour extends ComparableScala<Integer> {
    Hour minus(Hour value);
}
