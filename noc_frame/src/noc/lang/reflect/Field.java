package noc.lang.reflect;

import noc.annotation.Dependent;
import noc.annotation.RealType;

@Dependent
public class Field {

	public static final String Scala = "Scala";
	public static final String Inline = "Inline";
	public static final String Reference = "Reference";
	public static final String Cascade = "Cascade";

	String name;
	String displayName;
	boolean key = false;

	Type type;
	boolean array = false;

	@RealType(FieldReferType.class)
	String refer;

	public Field(String name, Type type) {
		super();
		this.name = name;
		this.displayName = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public String getDisplayName() {
		return this.name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
