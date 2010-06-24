package noc.frame.lang;

public interface Literal extends ComparableScala<String> {
	Bool matches(String regex);
}