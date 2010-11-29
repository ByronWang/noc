package noc.frame.vostore;

import noc.frame.Factory;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

public class VoStore extends DefaultStore<Vo> {
    public VoStore(Factory<?> parent, Type type) {
    	super(parent, type);
        this.parent = parent;
        this.type = type;
    }

    @Override
    public Vo getForUpdate(String key) {
        if (key == null) {
            return new VOImp(type);
        }
        return new VoAgent(items.get(key));
    }
}
