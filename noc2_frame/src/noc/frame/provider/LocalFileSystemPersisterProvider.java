package noc.frame.provider;

import java.io.File;

import noc.frame.Persister;
import noc.frame.model.Vo;
import noc.frame.persister.CsvFilePersister;

public class LocalFileSystemPersisterProvider extends BufferedProvider<Persister<Vo>>  {
	String pathName;
	File path;

	String[] exts = new String[] { ".xml", ".properties", ".csv" };

	public LocalFileSystemPersisterProvider(String pathName) {
		this.pathName = pathName;
	}

	@Override public void setup() {
		path = new File(pathName);
	}

	@Override protected Persister<Vo> find(String key) {
		File file;
		if ((file = new File(path, key + CsvFilePersister.EXT)).exists()) {
			return new CsvFilePersister(file);
//		} else if ((file = new File(path, key + PropertiesFilePersister.EXT)).exists()) {
//			return new PropertiesFilePersister(file);
//		} else if ((file = new File(path, key + XmlFilePersister.EXT)).exists()) {
//			return new XmlFilePersister(file);
		}
		return null;
	}

}
