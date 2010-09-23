package noc.frame.vostore;

import java.util.List;

import noc.frame.Factory;
import noc.frame.Persister;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VoPersistableStore extends VoStore {
	private static final Log log = LogFactory.getLog(VoPersistableStore.class);

	public Persister<String, Vo> persister;

	public VoPersistableStore(Factory parent, Type clz, Persister<String, Vo> persister) {
		super(parent, clz);
		this.persister = persister;
	}

	@Override
	public void setUp() {
		super.setUp();

		List<Vo> list = this.persister.list();
		log.debug("== Load init data from Persister ==");
		log.debug("  List Size : " + list.size());
		for (Vo v : list) {
			this.items.put(v.getIndentify(), new VoReadOnlyAgent(v));
			log.debug(v);
		}
	}

	@Override
	public Vo borrowData(String key) {
		if (key == null) {
			return new VOImp(type);
		}
		return new VoAgent(this.readData(key));
	}

	@Override
	public Vo returnData(String key, Vo v) {
		if (v instanceof VoAgent) {
			VoAgent vo = (VoAgent) v;
			if (vo.isChanged()) {
				items.put(key, v);
				return persister.returnData(key, v);
			} else {
				return vo.getSource();
			}
		} else {
			items.put(key, v);
			return persister.returnData(key, v);

		}
	}

}
