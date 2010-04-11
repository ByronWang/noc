package noc.lang.reflect;

import java.util.ArrayList;
import java.util.List;

import noc.annotation.FrameType;
import noc.annotation.Inline;

@FrameType
public class Type {

	final String displayName;
	final String name;
	final boolean scala;
	final boolean frameType;
	@Inline
	final List<Field> fields;
	final Type declaringType;

	public Type(String name, String displayName,  List<Field> fields,boolean scala,boolean frameType, Type declaringType) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.scala = scala;
		this.fields = fields;
		this.frameType = frameType;
		this.declaringType = declaringType;
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

	Field keyField;
	
	List<Field> keyFields;
	public Field getKeyField() {
		if (scala) {
			throw new UnsupportedOperationException(this.toString());
		}
		
		if(keyField !=null){
			return keyField;			
		}

		constructKeys();
		
		
		return keyField;
	}
	
	protected void constructKeys(){	
		keyFields = new ArrayList<Field>(10);
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).primaryKey) {
				keyFields.add(fields.get(i));
			}
		}
		
		if(keyFields.size() == 1){
			keyField = keyFields.get(0);
		}else if(keyFields.size() >1){
			
		}else if(keyFields.size() == 1){
			
		}			
	}
	
	//static final Field defaultKeyField = new Field("primaryKey", "key", null, true, false, false, true);
}
