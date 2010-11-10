package noc.lang;

import noc.frame.ComparableScala;

public interface Literal extends ComparableScala<String> {
    Bool matches(String regex);
}