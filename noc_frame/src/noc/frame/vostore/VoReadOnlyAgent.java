package noc.frame.vostore;

import noc.frame.vo.Vo;

public class VoReadOnlyAgent implements Vo {
	Vo source = null;
	boolean changed = false;

	public VoReadOnlyAgent(Vo source) {
		this.source = source;
	}

	@Override
	public Object get(String name) {
		return source.get(name);
	}

	@Override
	public String getIndentify() {
		return source.getIndentify();
	}

	@Override
	public void put(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCanonicalForm() {
		return source.getCanonicalForm();
	}

	@Override
	public Object get(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

}
