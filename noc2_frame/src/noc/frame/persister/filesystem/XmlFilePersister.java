package noc.frame.persister.filesystem;

import java.io.File;
import java.util.List;

import noc.frame.Vo;


public class XmlFilePersister extends FilePersister{
	public final static String EXT = ".xml";
	
	public XmlFilePersister(File file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	@Override public Vo get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public List<Vo> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public Vo update(Vo value) {
		// TODO Auto-generated method stub
		return null;
	}

}
