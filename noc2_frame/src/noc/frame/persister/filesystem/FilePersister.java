package noc.frame.persister.filesystem;

import java.io.File;

import noc.frame.persister.AbstractPersister;


public abstract class FilePersister<T> extends AbstractPersister<T>{
	protected File file;
	public FilePersister(File file){
		this.file = file; 
	}

}
