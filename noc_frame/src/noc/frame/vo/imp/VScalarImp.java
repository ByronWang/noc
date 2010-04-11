package noc.frame.vo.imp;

import noc.frame.vo.VScalar;

public class VScalarImp implements VScalar {

	@Override public String toString() {
		if (value != null) {
			return value.toString();
		} else {
			return "";
		}
	}

	Object value = null;

	public VScalarImp(Object v) {
		this.value = v;
	}

	@Override public Object getValue() {
		return value;
	}

	@Override public void setValue(Object v) {
		this.value = v;
	}

	@Override public String getCanonicalForm() {
		return "\"" + this.value.toString() + "\"";
	}

}
