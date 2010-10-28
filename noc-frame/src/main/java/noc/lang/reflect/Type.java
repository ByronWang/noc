package noc.lang.reflect;

import java.util.ArrayList;
import java.util.List;

import noc.annotation.FrameType;
import noc.annotation.Inline;
import noc.annotation.DisplayName;
import noc.annotation.RealType;

@FrameType
@DisplayName("Type")
public class Type {

	public static final String Master = "Master";
	public static final String Attribute = "Attribute";
	public static final String Underlying = "Underlying";
	public static final String Sequence = "Sequence";
	public static final String Scala = "Scala";
	public static final String Eembedded = "Eembedded";

	@DisplayName("名称")
	String name;
	String displayName;

	boolean standalone = true;

	@RealType(TypeMaterType.class)
	String master;

	Type declaringType = null;

	final @Inline
	@DisplayName("字段")
	List<Field> fields;

	// Type declaringType;

	public Type(String name) {
		super();
		this.name = name;

		this.fields = new ArrayList<Field>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public List<Field> getFields() {
		return fields;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public boolean isStandalone() {
		return standalone;
	}

	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}

	public Type getDeclaringType() {
		return declaringType;
	}

	public void setDeclaringType(Type declaringType) {
		this.declaringType = declaringType;
	}
}
