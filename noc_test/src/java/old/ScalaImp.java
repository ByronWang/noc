package old;

import noc.frame.Scala;

public class ScalaImp<O> implements Scala<O> {
	@Override public boolean equals(Object obj) {
		return value.equals(obj);
	}

	@Override public int hashCode() {
		return value.hashCode();
	}

	@Override public String toString() {
		return value.toString();
	}

	O value;
	ScalaImp() {
	}
	
	ScalaImp(O value){
		this.value = value;		
	}

	@Override public O ordinal() {
		return this.value;
	}

	@Override public void set(O value) {
		this.value = value;
	}

}
