package old;

import noc.lang.Bool;

public class BooleanImp extends ScalaImp<java.lang.Boolean> implements Bool {
	BooleanImp() {
		value = false;
	}
	
	static final Bool from(boolean value){
		return new BooleanImp(value);
	}

	BooleanImp(boolean value) {
		this.value = value;
	}

	@Override public Bool and(boolean compress) {
		return new BooleanImp(this.value && compress);
	}

	@Override public Bool and(Bool compress) {
		return new BooleanImp(this.value && compress.ordinal());
	}

	@Override public Bool not() {
		return  new BooleanImp(!value);
	}

	@Override public Bool or(boolean compress) {
		return  new BooleanImp(value || compress);
	}

	@Override public Bool or(Bool compress) {
		return  new BooleanImp(value || compress.ordinal());
	}
}
