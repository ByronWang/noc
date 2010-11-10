package noc.frame;

import noc.annotation.Abstract;
import noc.lang.Bool;

@Abstract
public interface ComparableScala<O> extends Scala<O> {

    /**
     * > : Greater Than
     * 
     * @param value
     * @return
     */
    Bool gt(O value);

    /**
     * >= : Greater than or Equivalent with
     * 
     * @param value
     * @return
     */
    Bool ge(O value);

    /**
     * < : Less than
     * 
     * @param value
     * @return
     */
    Bool lt(O value);

    /**
     * <= : Less than or Equivalent with
     * 
     * @param value
     * @return
     */
    Bool le(O value);

    /**
     * == : EQuivalent with
     * 
     * @param value
     * @return
     */
    Bool eq(O value);

    /**
     * != : Not Equivalent with
     * 
     * @param value
     * @return
     */
    Bool ne(O value);

}