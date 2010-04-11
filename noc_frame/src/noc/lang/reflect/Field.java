package noc.lang.reflect;

import noc.annotation.FrameType;

@FrameType public class Field {
	String displayName;

	String name;
	Type type;

	boolean array = false;
	boolean inline = false;
	boolean refer = false;
	boolean catalog = false;
	boolean primaryKey = false;

	public Field(String name, String displayName, Type type) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.type = type;
	}
	
	public Field(String name, String displayName, Type type, boolean primaryKey, boolean catalog, boolean list,
			boolean inline) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.primaryKey = primaryKey;
		this.type = type;
		this.array = list;
		this.catalog = catalog;
		this.inline = inline;
		this.refer = !type.isScala() && !inline;
	}

	public boolean isArray() {
		return array;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Type getType() {
		return type;
	}

	public boolean isInline() {
		return inline;
	}

	public boolean isRefer() {
		return refer;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isCatalog() {
		return catalog;
	}

	public void setCatalog(boolean catalog) {
		this.catalog = catalog;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

	public void setRefer(boolean refer) {
		this.refer = refer;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

}
