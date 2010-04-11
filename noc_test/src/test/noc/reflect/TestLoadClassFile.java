package noc.reflect;

import java.io.IOException;

import noc.framework.bytecode.LoadAll;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class TestLoadClassFile {
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			CannotCompileException {

		try {
			String definePath = "..\\noc_Define\\bin";
			String bizPath = "..\\noc_Biz\\bin";
			// String pathToname = "bin_dest";

			ClassPool pool = ClassPool.getDefault();
			pool.appendClassPath(definePath);
			pool.appendClassPath(bizPath);

			Loader cl = new Loader(pool);

			// cl.loadClass("data.sales.PricingDiff$SizeItem");
			cl.addTranslator(pool, new DissectionTranslator());

			 LoadAll la = new LoadAll(bizPath);
			 for (String classname : la.list()) {
			 Class<?> c=  cl.loadClass(classname);
			 System.out.println(c.getName());
			 //CtClass c = pool.get(classname);
			 //c.instrument(new VerboseEditor());
			 //System.out.println(c.toClass().getName());
							 
			 //c.writeFile(pathname);
			 }
			cl.loadClass("data.master.Employee");
//			cl.loadClass("data.sales.Order");
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class DissectionTranslator implements Translator {
		String pathToname = "bin_dest";

		CtClass cStandard;
		CtMethod cStandardGet;
		CtMethod cStandardset;
		CtClass string;
		CtClass scala;

		public void start(ClassPool pool) {

			try {
				scala = pool.get("noc.lang.Scala");
				string = pool.get("java.lang.String");
				cStandard = pool.get(this.getClass().getName() + "$MethodTemplate");
				cStandardGet = cStandard.getDeclaredMethod("getStandard");
				cStandardset = cStandard.getDeclaredMethod("setStandard");
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override public void onLoad(ClassPool pool, String classname) throws NotFoundException,
				CannotCompileException {
			final CtClass cls = pool.get(classname);

			for (CtField f : cls.getDeclaredFields()) {
				System.out.print(Modifier.toString(f.getModifiers()));
				System.out.print(" ");
				System.out.println(f.getName());

				// String newName ="_F_" + toHexString(f.getName());
				String newFieldName = f.getName();
				String newMethodName = f.getName();

				f.setName(newFieldName);
				// f.setType(string);
				f.setModifiers(Modifier.PRIVATE);

				if (f.getType().subtypeOf(scala)) {
					f.setType(string);

					// System.out.println(f.getName());

					CtMethod get = CtNewMethod.make("public java.lang.String get" + newMethodName
							+ "(){return this." + newFieldName + ";}", cls);

					CtMethod set = CtNewMethod.make("public void set" + newMethodName
							+ "(java.lang.String value){this." + newFieldName + " = value;}", cls);

					cls.addMethod(set);
					cls.addMethod(get);
				} else {
					CtMethod get = CtNewMethod.make("public " + f.getType().getName() + " get"
							+ newMethodName + "(){return this." + newFieldName + ";}", cls);
					CtMethod set = CtNewMethod.make(
							"public void set" + newMethodName + "(" + f.getType().getName()
									+ " value){this." + newFieldName + " = value;}", cls);

					cls.addMethod(set);
					cls.addMethod(get);
				}

			}

			for (CtConstructor c : cls.getConstructors()) {
				c.instrument(new ExprEditor() {
					FieldAccess lastRead = null;

					public void edit(FieldAccess arg) {
						try {
							//arg.where().getMethodInfo().;
							System.out.println("FieldAccess: "+ arg.where().getMethodInfo().getLineNumber(0) + "  ->" +  arg.where().getMethodInfo().getName()+ "[" + arg.getLineNumber()
									+ "] :");
							
							if (arg.isReader()) {
								lastRead = arg;
							} else {
								//System.out.println(arg.getFieldName() + ": " +  arg.getLineNumber());
								if (lastRead != null &&  arg.getLineNumber() == lastRead.getLineNumber()) {
									cls.getDeclaredMethod("get" + arg.getFieldName()).setBody(
											"return this." + lastRead.getFieldName() + ";");
									cls.getDeclaredMethod("set" + arg.getFieldName()).setBody(
											"this." + lastRead.getFieldName() + " = $1;");
									
									//arg.replace("{}");
//									lastRead.replace("$_=null;");
//									
//						            if (arg.getFieldName().equals(m_fieldName) && arg.isWriter()) {
//						                StringBuffer code = new StringBuffer();
//						                code.append("$0.");
//						                code.append(arg.getFieldName());
//						                code.append("=TranslateEditor.reverse($1);");
//						                arg.replace(code.toString());
//						            }
								}
							}
							// String dir = arg.isReader() ? "read" : "write";
							// System.out.println(" " + dir + " of " +
							// arg.getClassName() + "."
							// + arg.getFieldName());
						} catch (CannotCompileException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new RuntimeException(e);
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}

					@Override public void edit(ConstructorCall c) throws CannotCompileException {
						System.out.println("MethodCall: " + c.where().getMethodInfo().getLineNumber(0) + "  ->" + c.where().getMethodInfo().getName()+ "[" + c.getLineNumber()
								+ "] :");
						// TODO Auto-generated method stub
						super.edit(c);
					}

					@Override public void edit(MethodCall m) throws CannotCompileException {
						System.out.println("MethodCall: " + m.where().getMethodInfo().getLineNumber(0) + "  ->" +  m.where().getMethodInfo().getName()+ "[" + m.getLineNumber()
								+ "] :");
						super.edit(m);
					}
				});
				c.setBody("super();");
			}

			//cls.instrument(new InspectExprEditor());

			try {
				cls.writeFile(pathToname);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Modified @interface Modified {

		}

		public class MethodTemplate {
			@Modified public String getStandard() {
				return null;
			}

			@Modified public void setStandard(String value) {

			}
		}

	}

}
