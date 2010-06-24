package noc.frame.persister.filesystem;

import java.io.File;
import java.util.List;


public class XmlFilePersister<T> extends FilePersister<T> {
	final static String EXT = ".xml";
	
	public XmlFilePersister(File file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	@Override public T get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public List<T> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override public T update(T value) {
		// TODO Auto-generated method stub
		return null;
	}	
}
