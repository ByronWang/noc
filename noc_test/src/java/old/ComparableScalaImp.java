package old;

import noc.frame.ComparableScala;
import noc.lang.Bool;

public class ComparableScalaImp<T extends java.lang.Comparable<T>>  extends ScalaImp<T> implements ComparableScala<T> {

	ComparableScalaImp() {
	}
	
	ComparableScalaImp(T value) {
		super(value);
	}

	@Override public Bool eq(T value) {
		return new BooleanImp(value.compareTo(value)==0);
	}

	@Override public Bool ge(T value) {
		return new BooleanImp(value.compareTo(value)>=0);
	}

	@Override public Bool gt(T value) {
		return new BooleanImp(value.compareTo(value)>0);
	}

	@Override public Bool le(T value) {
		return new BooleanImp(value.compareTo(value)<=0);
	}

	@Override public Bool lt(T value) {
		return new BooleanImp(value.compareTo(value)<0);
	}

	@Override public Bool ne(T value) {
		return new BooleanImp(value.compareTo(value)!=0);
	}


}
