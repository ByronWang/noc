package noc.lang;

public interface Name extends Literal {
	Bool matches(String regex);
}
