package noc.frame.persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import noc.frame.Persister;
import noc.frame.model.FreeVo;
import noc.frame.model.ListMap;
import noc.frame.model.Vo;

public class CsvFilePersister  implements Persister<Vo> {

	public final static String EXT = ".csv";
	final static String SEPERATOR = ",";

	List<String> headers = new ArrayList<String>();
	
	ListMap<String, Vo> voList ;
	protected File file;
	
	public CsvFilePersister(File file) {
		this.file = file;
	}

	@Override public void setup() {
		voList = new ListMap<String, Vo>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = null;
			
			str = br.readLine();
			
			if(str==null){
				throw new RuntimeException();
			}
			
			headers = toHeaders(str);

			while ((str = br.readLine()) != null) {
				Vo vo = toObject(str);				
				voList.put(vo.getReferID(),vo);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override public Vo get(String key) {
		throw new UnsupportedOperationException();
	}

	protected List<String> toHeaders(String str) {
		List<String> hs = new ArrayList<String>();
		String[] sa = str.split(SEPERATOR);
		for (int i = 0; i < sa.length; i++) {
			hs.add(sa[i]);
		}
		return hs;
	}

	protected Vo toObject(String str) {
		FreeVo vo = new FreeVo();

		String[] sa = str.split(SEPERATOR);
		for (int i = 0; i < sa.length; i++) {
			vo.put(headers.get(i), sa[i]);
		}
		return vo;
	}

	@Override public List<Vo> list() {
		ArrayList<Vo> voList = new ArrayList<Vo>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine(); // ignore HEAD

			String str = null;
			while ((str = br.readLine()) != null) {
				voList.add(toObject(str));
			}

			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return (List<Vo>) voList;
	}

	@Override public Vo update(Vo value) {
		if (value instanceof FreeVo) {
			try {
				FreeVo freeVo = (FreeVo) value;

				for (String key : freeVo.getKeys()) {
					boolean find = false;
					for (int i = 0; i < headers.size(); i++) {
						if (key.equals(headers.get(i))) {
							find = true;
							break;
						}
					}

					if (find) continue;
					headers.add(key);
				}

				FileWriter fw = new FileWriter(file);

				for (int i = 0; i < headers.size(); i++) {
					fw.write(headers.get(i) + ",");
				}
				fw.write("\r\n");

				for (int i = 0; i < headers.size(); i++) {
					fw.write(freeVo.get(headers.get(i)).toString() + ",");
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
		return "CSV File[" + this.file.getAbsolutePath() + "]";
	}

	@Override public void cleanup() {
		
	}

}
