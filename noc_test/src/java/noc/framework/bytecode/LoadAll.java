package noc.framework.bytecode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class LoadAll {
	final String path;
	public LoadAll(String path){
		this.path = path;
	}
	
	public List<String> list(){
		File f = new File(path);
		
		List<String> rt = new ArrayList<String>();
		
		if(!f.exists())
			return rt;
		
		if(f.isFile() &&  path.endsWith(".jar")){
			try {
				JarFile jf = new JarFile(path);
				Enumeration<JarEntry> en = jf.entries();
				while (en.hasMoreElements()) {
					String name = en.nextElement().getName();
					if(name.endsWith(".class")){
						rt.add(name);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}else if(f.isDirectory()){
			listClassFile(f,rt);
		}else if(f.getName().endsWith(".class")){
			rt.add(f.getName());
		}
		return rt;
	}
	
	private void listClassFile(File d,List<String> list){
		for(File f : d.listFiles()){
			if(f.isFile() && f.getName().endsWith(".class")){
				list.add(ToClassName(path, f.getPath()));					
			}else if(f.isDirectory()){
				listClassFile(f,list);
			}
		}
	}
	
	final static String ToClassName(String pathname,String filename){
		return filename.substring(pathname.length()+1, filename.length() - 6).replace('\\', '.');
	}
}
