package noc.frame.vostore;


import noc.frame.EntityFactory;
import noc.frame.vo.Vo;
import noc.frame.vo.imp.VOImp;
import noc.lang.reflect.Type;

public class VoFactory implements EntityFactory<Vo> {
	final Type type;
	public VoFactory(Type type) {
		this.type = type;
	}
	
	@Override
	public Vo decorate(Vo t) {
		return new VoAgent(t);
	}

	@Override
	public Vo newInstance() {
		return new VOImp(type);
	}

}
