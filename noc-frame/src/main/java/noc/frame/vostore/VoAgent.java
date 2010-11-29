package noc.frame.vostore;

import java.util.HashMap;
import java.util.Map;

import noc.frame.Agent;
import noc.frame.Identifiable;
import noc.frame.vo.Vo;

public class VoAgent implements Vo, Identifiable, Agent {
	protected Map<String, Object> changedData = new HashMap<String, Object>();
	protected Vo source = null;
	protected boolean beModified = false;

	public VoAgent(Vo source) {
		this.source = source;
	}

	@Override
	public Object get(String name) {
		if (changedData.containsKey(name)) {
			return changedData.get(name);
		} else {
			return source.get(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see noc.frame.vostore.Agent#getId()
	 */
	@Override
	public String getId() {
		return source.getId();
	}

	@Override
	public void put(String name, Object v) {
		Object oldValue = this.source.get(name);

		if (v instanceof Object) {
			if (oldValue != v) {
				changedData.put(name, v);
				beModified = true;
			}
		} else if (oldValue != null) {
			if (!oldValue.equals(v)) {
				changedData.put(name, v);
				beModified = true;
			}
		}

		// changed = this.changedData.size() < 10;//TODO Delete add for test
	}

	@Override
	public String getCanonicalForm() {
		return changedData.toString();
	}

	public Map<String, Object> getChangedData() {
		return changedData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see noc.frame.vostore.Agent#getSource()
	 */
	public Vo getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see noc.frame.vostore.Agent#isBeModified()
	 */
	public boolean isBeModified() {
		return beModified;
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
