package noc.lang.reflect;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import noc.frame.Scala;
import noc.frame.Store;
import noc.lang.Bool;
import noc.lang.Name;

public class TypeReadonlyStore implements Store<String,Type> {

//	private static final Log log =LogFactory.getLog(TypeReadonlyStore.class);
	
	ClassPool pool;
	Map<String, Type> types;
	TypeReader reader;

	public TypeReadonlyStore() {
		types = new HashMap<String, Type>();		
		this.pool = ClassPool.getDefault();
	}

	@Override
	public void setUp() {
		try {
			reader = new TypeReader(this, pool.get(Scala.class.getName()));

			Type boolType = readData(Bool.class.getName());
			this.types.put("boolean", boolType);
			this.types.put(boolType.getName(), boolType);

			Type nameType = readData(Name.class.getName());
			this.types.put("java.lang.String", nameType);
			this.types.put(nameType.getName(), nameType);

			this.readData(Type.class.getName());
			this.readData(Field.class.getName());
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public TypeReadonlyStore(ClassPool pool) {
		this.pool = pool;
	}

    public void load(String path){
        load(new File(path));
    }
    public void load(File path){
        if(path.isDirectory()){
            loadFolder(path, path);
        }else if(path.getName().endsWith(".jar")){
            loadJar(path);
        }else{
            throw new RuntimeException();            
        }
    }

    public void loadJar(String path) {
        loadJar(new File(path));
    }
	public void loadJar(File f) {
	    this.appendClassPath(f.getPath());
		try {
			JarFile jf = new JarFile(f);
			Enumeration<JarEntry> en = jf.entries();
			while (en.hasMoreElements()) {
				String name = en.nextElement().getName();
				if (name.endsWith(".class")) {
					name = name.substring(0, name.length() - ".class".length()).replace('\\', '.').replace('/', '.');
					this.readData(name);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void loadFolder(File root, File d) {
		try {
			if (!d.exists() || !d.isDirectory()) return;

			for (File f : d.listFiles()) {
				if (f.isFile() && f.getName().endsWith(".class")) {
					String name = f.getPath().substring(root.getPath().length() + 1);

					name = name.substring(0, name.length() - ".class".length()).replace('\\', '.').replace('/', '.');

					this.readData(name);
				} else if (f.isDirectory()) {
					loadFolder(root, f);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void appendClassPath(String path) {
		try {
			pool.appendClassPath(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void loadFolder(String path) {
		try {
			pool.appendClassPath(path);
			File f = new File(path);
			loadFolder(f, f);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Type readData(String name) {
		try {
			Type type = types.get(name);
			if (type == null) {
				CtClass clz = pool.get(name);
				type = new Type(name);
				types.put(name, type);
				return reader.fillFrom(clz, type);
			}
			return type;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Type> list() {
		ArrayList<Type> types = new ArrayList<Type>();
		for (Type type : this.types.values()) {
			types.add(type);
		}
		return types;
	}

	public List<Type> listScala() {
		ArrayList<Type> types = new ArrayList<Type>();
		for (Type type : this.types.values()) {
			if (type.master.equals(Type.Scala)) {
				types.add(type);
			}
		}
		return types;
	}

	@Override
	public Type returnData(String key, Type v) {
		throw new UnsupportedOperationException(v.toString());
	}

	@Override
	public Type borrowData(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tearDown() {
		// TODO Auto-generated method stub
		
	}

}
