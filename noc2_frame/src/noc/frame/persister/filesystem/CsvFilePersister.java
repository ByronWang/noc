package noc.frame.persister.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import noc.frame.FreeVo;
import noc.frame.Referable;

public class CsvFilePersister<T extends Referable> extends FilePersister<T> {

	final static String EXT = ".csv";
	final static String SEPERATOR = ",";

	ArrayList<String> fields = new ArrayList<String>();

	public CsvFilePersister(File file) {
		super(file);
	}

	@Override public void setup() {
		super.setup();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] sa = br.readLine().split(SEPERATOR);
			for (int i = 0; i < sa.length; i++) {
				fields.add(sa[i]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override public T get(String key) {
	
		return null;
	}

	@SuppressWarnings("unchecked") 
	@Override public List<T> list() {
		ArrayList<FreeVo> voList = new ArrayList<FreeVo>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine(); // ignore HEAD
			
			String str = null;
			while((str=br.readLine())!=null){
				FreeVo freeVo = new FreeVo();
				
				String[] sa = str.split(SEPERATOR);
				for (int i = 0; i < sa.length; i++) {
					freeVo.put(fields.get(i), sa[i]);
				}
				voList.add(freeVo);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return (List<T>)voList;
	}

	@Override public T update(T value) {
		if (value instanceof FreeVo) {
			try {
				FreeVo freeVo = (FreeVo) value;

				for (String key : freeVo.getKeys()) {
					boolean find = false;
					for (int i = 0; i < fields.size(); i++) {
						if (key.equals(fields.get(i))) {
							find = true;
							break;
						}
					}

					if (find) continue;
					fields.add(key);
				}

				FileWriter fw = new FileWriter(file);

				for (int i = 0; i < fields.size(); i++) {
					fw.write(fields.get(i) + ",");
				}
				fw.write("\r\n");

				for (int i = 0; i < fields.size(); i++) {
					fw.write(freeVo.get(fields.get(i)).toString() + ",");
				}

				fw.close();
				return value;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException();
	}

	@Override public String toString() {
		// TODO Auto-generated method stub
		return "CSV File[" + super.file.getAbsolutePath() + "]";
	}

}
