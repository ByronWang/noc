package noc.frame.persister.filesystem;

import java.io.File;

import noc.frame.Persister;
import noc.frame.Referable;

public class LocalFileSystemPersisterProvider<V extends Referable> extends FileSystemPersisterProvider<V> {
	String pathName;
	File path;

	String[] exts = new String[] { ".xml", ".properties", ".csv" };

	public LocalFileSystemPersisterProvider(String pathName) {
		this.pathName = pathName;
	}

	@Override public void setup() {
		path = new File(pathName);
	}

	@Override protected Persister<V> find(String key) {
		File file;
		if ((file = new File(path, key + CsvFilePersister.EXT)).exists()) {
			return new CsvFilePersister<V>(file);
		} else if ((file = new File(path, key + PropertiesFilePersister.EXT)).exists()) {
			return new PropertiesFilePersister<V>(file);
		} else if ((file = new File(path, key + XmlFilePersister.EXT)).exists()) {
			return new XmlFilePersister<V>(file);
		}
		return null;
	}

}
