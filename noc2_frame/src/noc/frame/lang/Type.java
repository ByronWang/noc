package noc.frame.lang;

import java.util.List;


public interface Type {

	String getName();

	List<Field> getFields();

	boolean isScala();

	Field getPrimaryKeyField();

}
