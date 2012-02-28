package util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintObejct {

    final static int ALLOW_LEVEL = 1;

    public static void print(Class<?> clazz, Object instance) {
        Log log = LogFactory.getLog(instance.getClass());

        log.trace("");
        log.trace("=====Start PrintObject ========   " + clazz.getName() + "  ===============");
        print(clazz, instance, 0);
        log.trace("-----End   PrintObject --------   " + clazz.getName() + "  ---------------");
        log.trace("");
    }

    private static String indent(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "\t";
        }
        return indent;
    }

    private static void print(Class<?> clz, Object instance, int level) {
        Log log = LogFactory.getLog(instance.getClass());
        // Class<?> clz = instance.getClass();

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
                    Class<?> returnType = m.getReturnType();
                    if (returnType.isArray()) {
                        log.trace(indent(level) + m.getName() + ">>  [" + returnType.getName() + "]");
                        Object[] va = (Object[]) m.invoke(instance);
                        if (va == null) {
                            log.trace(indent(level), null);
                            continue;
                        }
                        for (int i = 0; i < va.length; i++) {
                            log.trace(indent(level + 1) + va[i].toString());
                        }
                    } else if (returnType.isPrimitive()) {
                        log.trace(indent(level) + m.getName() + ": " + m.invoke(instance).toString());
                    } else if (m.getName().endsWith("Stream") || m.getName().endsWith("Reader")
                            || m.getName().endsWith("Writer")) {
                        log.trace(indent(level) + m.getName() + "[[ " + m.getReturnType().getName() + " ]]");
                    } else {
                        Object returnValue = m.invoke(instance);
                        if (returnValue == null) {
                            log.trace(indent(level) + m.getName() + ": " + null);
                        } else if (returnValue instanceof String) {
                            log.trace(indent(level) + m.getName() + ": " + (String) returnValue);
                        } else if (returnValue instanceof Class<?>) {
                            log.trace(indent(level) + m.getName() + "[[ Class<" + ((Class<?>) returnValue).getName()
                                    + "> ]]");
                        } else if (returnValue instanceof Iterable<?>) {
                            log.trace(indent(level) + m.getName() + ">> [" + returnType.getName() + "]");
                            Iterable<?> va = (Iterable<?>) returnValue;

                            if (m.getName().endsWith("Names")) {
                                String getName = m.getName().substring(0, m.getName().length() - "Names".length());
                                Method getitem = clz.getMethod(getName, String.class);

                                if (getitem != null) {
                                    for (Object o : va) {
                                        String key = o.toString();
                                        log.trace(indent(level + 1) + key + ": "
                                                + getitem.invoke(instance, key).toString());
                                    }
                                    continue;
                                }
                            }

                            for (Object o : va) {
                                log.trace(indent(level + 1) + o.toString());
                            }
                        } else if (returnValue instanceof Enumeration<?>) {
                            log.trace(indent(level) + m.getName() + ">> [" + returnType.getName() + "]");
                            Enumeration<?> va = (Enumeration<?>) returnValue;

                            if (m.getName().endsWith("Names")) {
                                String getName = m.getName().substring(0, m.getName().length() - "Names".length());
                                Method getitem = clz.getMethod(getName, String.class);

                                if (getitem != null) {
                                    while (va.hasMoreElements()) {
                                        String key = va.nextElement().toString();
                                        log.trace(indent(level + 1) + key + ": "
                                                + getitem.invoke(instance, key).toString());
                                    }
                                    continue;
                                }
                            }

                            while (va.hasMoreElements()) {
                                log.trace(indent(level + 1) + va.nextElement().toString());
                            }

                        } else if (returnValue instanceof Serializable) {
                            log.trace(indent(level) + m.getName() + ">> [" + returnType.getName() + "]");
                            Serializable so = (Serializable) returnValue;
                            if (level < ALLOW_LEVEL) {
                                print(returnType, so, level + 1);
                            }
                        } else {
                            if (level < ALLOW_LEVEL) {
                                log.trace(indent(level) + m.getName() + ">> [" + returnType.getName() + "]");
                                print(returnType, returnValue, level + 1);
                            } else {
                                log.trace(indent(level) + m.getName() + returnType.getName() + "[["
                                        + returnValue.toString() + "]]");

                            }
                            // log.trace(m.getName(), "[[ " +
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
