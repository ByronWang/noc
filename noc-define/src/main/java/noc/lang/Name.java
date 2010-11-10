package noc.lang;

public interface Name extends Literal {
    @Override
    Bool matches(String regex);
}
