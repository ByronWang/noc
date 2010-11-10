package noc.lang;

public interface Status extends Literal {
    @Override
    Bool matches(String regex);
}
