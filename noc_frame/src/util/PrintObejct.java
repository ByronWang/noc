package util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import sun.security.util.Debug;

public class PrintObejct {

	final static int ALLOW_LEVEL = 1;

	public static void print(Object object) {
		System.out.println("");
		System.out.println("=====Start PrintObject ========   " + object.getClass().getName()
				+ "  ===============");
		print(object, 0);
		System.out.println("-----End   PrintObject --------   " + object.getClass().getName()
				+ "  ---------------");
		System.out.println("");
	}

	private static String indent(int level) {
		String indent = "";
		for (int i = 0; i < level; i++) {
			indent += "\t";
		}
		return indent;
	}

	private static void print(Object object, int level) {
		Class<?> clz = object.getClass();

		try {
			for (Method m : clz.getMethods()) {
				if (Modifier.isStatic(m.getModifiers())) {
					continue;
				}
				if (m.getName().equals("getClass")) {
					continue;
				}

				if (m.getParameterTypes().length == 0 && m.getName().startsWith("get")) {
					if (m.getAnnotation(Deprecated.class) != null) {
						continue;
					}
					Class<?> rType = m.getReturnType();
					if (rType.isArray()) {
						Debug.println(indent(level) + m.getName(), ">>  [" + rType.getName() + "]");
						Object[] va = (Object[]) m.invoke(object);
						if (va == null) {
							Debug.println(indent(level), null);
							continue;
						}
						for (int i = 0; i < va.length; i++) {
							Debug.println(indent(level + 1), va[i].toString());
						}
					} else if (rType.isPrimitive()) {
						Debug.println(indent(level) + m.getName(), m.invoke(object).toString());
					} else if (m.getName().endsWith("Stream") || m.getName().endsWith("Reader")
							|| m.getName().endsWith("Writer")) {
						Debug.println(indent(level) + m.getName(), "[[ "
								+ m.getReturnType().getName() + " ]]");
					} else {
						Object returnValue = (Object) m.invoke(object);
						if (returnValue == null) {
							Debug.println(indent(level) + m.getName(), null);
						} else if (returnValue instanceof String) {
							Debug.println(indent(level) + m.getName(), (String) returnValue);
						} else if (returnValue instanceof Class<?>) {
							Debug.println(indent(level) + m.getName(), "[[ Class<"
									+ ((Class<?>) returnValue).getName() + "> ]]");
						} else if (returnValue instanceof Iterable<?>) {
							Debug.println(indent(level) + m.getName(), ">> [" + rType.getName() + "]");
							Iterable<?> va = (Iterable<?>) returnValue;

							if (m.getName().endsWith("Names")) {
								String getName = m.getName().substring(0,
										m.getName().length() - "Names".length());
								Method getitem = clz.getMethod(getName, String.class);

								if (getitem != null) {
									for (Object o : va) {
										String key = o.toString();
										Debug.println(indent(level + 1) + key, getitem.invoke(
												object, key).toString());
									}
									continue;
								}
							}

							for (Object o : va) {
								Debug.println(indent(level) + 1, o.toString());
							}
						} else if (returnValue instanceof Enumeration<?>) {
							Debug.println(indent(level) + m.getName(), ">> [" + rType.getName() + "]");
							Enumeration<?> va = (Enumeration<?>) returnValue;

							if (m.getName().endsWith("Names")) {
								String getName = m.getName().substring(0,
										m.getName().length() - "Names".length());
								Method getitem = clz.getMethod(getName, String.class);

								if (getitem != null) {
									while (va.hasMoreElements()) {
										String key = va.nextElement().toString();
										Debug.println(indent(level + 1) + key, getitem.invoke(
												object, key).toString());
									}
									continue;
								}
							}

							while (va.hasMoreElements()) {
								Debug.println(indent(level + 1), va.nextElement().toString());
							}

						} else if (returnValue instanceof Serializable) {
							Debug.println(indent(level) + m.getName(), ">> [" + rType.getName() + "]");
							Serializable so = (Serializable) returnValue;
							if (level < ALLOW_LEVEL) {
								print(so, level + 1);
							}
						} else {
							if (level < ALLOW_LEVEL) {
								Debug.println(indent(level) + m.getName(), ">> [" + rType.getName() + "]");
								print(returnValue, level + 1);
							} else {
								Debug.println(indent(level) + m.getName(), rType.getName() + "[["
										+ returnValue.toString() + "]]");

							}
							// Debug.println(m.getName(), "[[ " +
							// m.getReturnType().getName() + " ]]");
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
