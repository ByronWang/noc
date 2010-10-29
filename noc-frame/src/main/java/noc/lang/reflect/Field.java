package noc.lang.reflect;

import noc.annotation.Dependent;
import noc.annotation.DisplayName;
import noc.annotation.RealType;

@Dependent
public class Field {

	public static final String Scala = "Scala";
	public static final String Inline = "Inline";
	public static final String Reference = "Reference";
	public static final String Cascade = "Cascade";
	
	public static final String PrimaryKey = "PrimaryKey";
	public static final String Core = "Core";
	public static final String Important = "Important";
	public static final String Normal = "Normal";
//	importance

    @DisplayName("名称")
	String name;
    @DisplayName("显示名称")
	String displayName;
    @DisplayName("重要性")
	String importance = Normal;

    @DisplayName("类型")
	Type type;
    @DisplayName("数组")
	boolean array = false;

	@RealType(FieldReferType.class)
    @DisplayName("引用类型")
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }


}
