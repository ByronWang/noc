/**
 * 
 */
package noc.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target( { FIELD }) 
@AutoWireByName("key;id;code;name;ID;Name;NAME姓名;名称;Code;CODE;") 
public @interface PrimaryKey {}