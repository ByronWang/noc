/**
 * 
 */
package noc.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import noc.lang.Name;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
@AutoWireByName("key;name;Name;NAME;姓名;名称;")
@AutoWireByType({ Name.class })
public @interface Core {
}