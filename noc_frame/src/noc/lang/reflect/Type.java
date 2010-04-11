package noc.lang.reflect;

import java.util.ArrayList;
import java.util.List;

import noc.annotation.FrameType;
import noc.annotation.Inline;

@FrameType public class Type {

	String displayName;
	String name;
	boolean scala;
	boolean frameType;
	Field keyField;
	final List<Field> keyFields;
	
	@Inline final List<Field> fields;
	Type declaringType;

	public Type(String name, String displayName, boolean scala, boolean frameType, Type declaringType) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.scala = scala;		
		this.frameType = frameType;
		this.declaringType = declaringType;
		
		this.fields = new ArrayList<Field>();
		this.keyFields = new ArrayList<Field>();
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public boolean isScala() {
		return scala;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Type getDeclaringType() {
		return declaringType;
	}


	public Field getKeyField() {
		if (scala) {
			throw new UnsupportedOperationException(this.toString());
		}

		if (keyField != null) {
			return keyField;
		}

		constructKeys();

		return keyField;
	}

	protected void constructKeys() {
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).primaryKey) {
				keyFields.add(fields.get(i));
			}
		}

		if (keyFields.size() == 1) {
			keyField = keyFields.get(0);
		} else if (keyFields.size() > 1) {

		} else if (keyFields.size() == 1) {

		}
	}

	public boolean isFrameType() {
		return frameType;
	}

	public void setFrameType(boolean frameType) {
		this.frameType = frameType;
	}

	public List<Field> getKeyFields() {
		return keyFields;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScala(boolean scala) {
		this.scala = scala;
	}

	public void setDeclaringType(Type declaringType) {
		this.declaringType = declaringType;
	}

	public void setKeyField(Field keyField) {
		this.keyField = keyField;
	}

}
