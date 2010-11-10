/**
 * 
 */
package noc.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import noc.lang.Code;
import noc.lang.ID;
import noc.lang.Name;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
@AutoWireByName("key;id;code;name;ID;Name;NAME;姓名;名称;Code;CODE;")
@AutoWireByType({ Name.class, Code.class, ID.class })
public @interface PrimaryKey {
}