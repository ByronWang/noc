package noc.lang.reflect;

import noc.annotation.Data;
import noc.frame.Scala;

@Data.DefaultValue("Scala")
@Data.ValueList({ Field.PrimaryKey, Field.Important, Field.Normal, Field.Cascade })
public interface FieldImportance extends Scala<java.lang.String> {

}
