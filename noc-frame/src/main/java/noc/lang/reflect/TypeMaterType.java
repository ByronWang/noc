package noc.lang.reflect;

import noc.annotation.Data;
import noc.frame.Scala;

@Data.DefaultValue("Master")
@Data.ValueList({ "Master", "Attribute", "Sequence", "Scala" })
public interface TypeMaterType extends Scala<java.lang.String> {

}
