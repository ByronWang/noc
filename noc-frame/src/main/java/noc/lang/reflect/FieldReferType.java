package noc.lang.reflect;

import noc.annotation.Data;
import noc.frame.Scala;

@Data.DefaultValue("Scala")
@Data.ValueList({ Field.Scala, Field.Inline, Field.Reference, Field.Cascade })
public interface FieldReferType extends Scala<java.lang.String> {

}
