package noc.frame.lang;


public interface Field {

	boolean isArray();

	Type getType();

	boolean isInline();

	boolean isRefer();

	String getName();

}
