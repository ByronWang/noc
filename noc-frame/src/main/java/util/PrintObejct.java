package util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintObejct {

    final static int ALLOW_LEVEL = 1;

    public static void print(Object object) {
        Log log = LogFactory.getLog(object.getClass());

        log.trace("");
        log.trace("=====Start PrintObject ========   " + object.getClass().getName() + "  ===============");
        print(object, 0);
        log.trace("-----End   PrintObject --------   " + object.getClass().getName() + "  ---------------");
        log.trace("");
    }

    private static String indent(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "\t";
        }
        return indent;
    }

    private static void print(Object object, int level) {
        Log log = LogFactory.getLog(object.getClass());
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
                        log.trace(indent(level) + m.getName() + ">>  [" + rType.getName() + "]");
                        Object[] va = (Object[]) m.invoke(object);
                        if (va == null) {
                            log.trace(indent(level), null);
                            continue;
                        }
                        for (int i = 0; i < va.length; i++) {
                            log.trace(indent(level + 1) + va[i].toString());
                        }
                    } else if (rType.isPrimitive()) {
                        log.trace(indent(level) + m.getName() + ": " + m.invoke(object).toString());
                    } else if (m.getName().endsWith("Stream") || m.getName().endsWith("Reader")
                            || m.getName().endsWith("Writer")) {
                        log.trace(indent(level) + m.getName() + "[[ " + m.getReturnType().getName() + " ]]");
                    } else {
                        Object returnValue = m.invoke(object);
                        if (returnValue == null) {
                            log.trace(indent(level) + m.getName() + ": " + null);
                        } else if (returnValue instanceof String) {
                            log.trace(indent(level) + m.getName() + ": " + (String) returnValue);
                        } else if (returnValue instanceof Class<?>) {
                            log.trace(indent(level) + m.getName() + "[[ Class<" + ((Class<?>) returnValue).getName()
                                    + "> ]]");
                        } else if (returnValue instanceof Iterable<?>) {
                            log.trace(indent(level) + m.getName() + ">> [" + rType.getName() + "]");
                            Iterable<?> va = (Iterable<?>) returnValue;

                            if (m.getName().endsWith("Names")) {
                                String getName = m.getName().substring(0, m.getName().length() - "Names".length());
                                Method getitem = clz.getMethod(getName, String.class);

                                if (getitem != null) {
                                    for (Object o : va) {
                                        String key = o.toString();
                                        log.trace(indent(level + 1) + key + ": "
                                                + getitem.invoke(object, key).toString());
                                    }
                                    continue;
                                }
                            }

                            for (Object o : va) {
                                log.trace(indent(level + 1) + o.toString());
                            }
                        } else if (returnValue instanceof Enumeration<?>) {
                            log.trace(indent(level) + m.getName() + ">> [" + rType.getName() + "]");
                            Enumeration<?> va = (Enumeration<?>) returnValue;

                            if (m.getName().endsWith("Names")) {
                                String getName = m.getName().substring(0, m.getName().length() - "Names".length());
                                Method getitem = clz.getMethod(getName, String.class);

                                if (getitem != null) {
                                    while (va.hasMoreElements()) {
                                        String key = va.nextElement().toString();
                                        log.trace(indent(level + 1) + key + ": "
                                                + getitem.invoke(object, key).toString());
                                    }
                                    continue;
                                }
                            }

                            while (va.hasMoreElements()) {
                                log.trace(indent(level + 1) + va.nextElement().toString());
                            }

                        } else if (returnValue instanceof Serializable) {
                            log.trace(indent(level) + m.getName() + ">> [" + rType.getName() + "]");
                            Serializable so = (Serializable) returnValue;
                            if (level < ALLOW_LEVEL) {
                                print(so, level + 1);
                            }
                        } else {
                            if (level < ALLOW_LEVEL) {
                                log.trace(indent(level) + m.getName() + ">> [" + rType.getName() + "]");
                                print(returnValue, level + 1);
                            } else {
                                log.trace(indent(level) + m.getName() + rType.getName() + "[[" + returnValue.toString()
                                        + "]]");

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
