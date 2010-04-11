package old;

import noc.lang.Bool;
import noc.lang.Name;

public class NameImpl extends ComparableScalaImp<String> implements Name {

	public NameImpl() {
	}
	
	NameImpl(String value) {
		super(value);
	}

	@Override public String toString() {
		return value.toString();
	}

	@Override public Bool matches(String regex) {
		return  new BooleanImp(this.value.matches(regex));
	}

}
