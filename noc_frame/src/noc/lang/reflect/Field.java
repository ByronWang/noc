package noc.lang.reflect;

import noc.annotation.FrameType;


@FrameType
public class Field {
	final String displayName;
	final String name;
	final Type type;
	
	final boolean array;
	final boolean inline;
	final boolean refer;
	final boolean catalog;
	final boolean primaryKey;

	public Field(String name, String displayName,Type type, boolean primaryKey,boolean catalog, boolean list,
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

}
