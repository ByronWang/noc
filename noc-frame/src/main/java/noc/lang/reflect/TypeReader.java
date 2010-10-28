package noc.lang.reflect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;
import noc.annotation.Attribute;
import noc.annotation.AutoWireByName;
import noc.annotation.AutoWireByType;
import noc.annotation.Core;
import noc.annotation.Dependent;
import noc.annotation.DisplayName;
import noc.annotation.Important;
import noc.annotation.Inline;
import noc.annotation.Master;
import noc.annotation.PrimaryKey;
import noc.annotation.RealType;
import noc.annotation.Reference;
import noc.annotation.Sequence;
import noc.frame.Store;

public class TypeReader {

    private static final Log log = LogFactory.getLog(TypeReader.class);

    final Store<String, Type> types;
    final CtClass clzScala;

    TypeReader(Store<String, Type> types, CtClass scala) {
        this.types = types;
        this.clzScala = scala;
    }

    Type fillFrom(CtClass clz, Type type) throws ClassNotFoundException, NotFoundException {
        Object an = null;
        an = clz.getAnnotation(DisplayName.class);
        log.debug(type.name + " : " + an);
        if (an != null) {
            type.displayName = ((DisplayName) an).value();
        } else {
            type.displayName = type.getName();
        }

        // type.displayName = (an = clz.getAnnotation(DisplayName.class)) !=
        // null ? ((DisplayName) an).value() : clz
        // .getName();

        // // Handle Frame Type
        // boolean frameType = clz.getAnnotation(FrameType.class) != null;

        type.master = Type.Master;

        if (clz.getFields().length < 1) {
            if (clz.isPrimitive() || clz.subtypeOf(clzScala)) {
                type.master = Type.Scala;
            } else {
                type.master = Type.Underlying;
            }
            type.standalone = false;
            return type;
        }

        if (clz.getDeclaringClass() != null) {
            type.master = Type.Eembedded;
            type.declaringType = types.readData(clz.getDeclaringClass().getName());
            type.standalone = false;
        } else {
            type.standalone = true;
        }

        if (clz.getAnnotation(Master.class) != null) {
            type.master = Type.Master;
        } else if (clz.getAnnotation(Sequence.class) != null) {
            type.master = Type.Sequence;
        } else if (clz.getAnnotation(Attribute.class) != null) {
            type.master = Type.Attribute;
        }

        if (clz.getAnnotation(Dependent.class) != null) {
            type.standalone = false;
            type.master = Type.Eembedded;
        }

        // Construct type
        CtField[] cfs = clz.getFields();

        // type = types.get(name);

        List<Field> fs = type.fields;
        for (int i = 0; i < cfs.length; i++) {
            if (!Modifier.isStatic(cfs[i].getModifiers())) {
                fs.add(readTo(type, cfs[i]));
            }
        }

        int countKey = 0;
        for (Field f : fs) {
            if (f.importance == Field.PrimaryKey)
                countKey++;
        }

        // 如果没有Key的话,按字段名称寻找PrimaryKey中定义的可以默认作为Key的字段
        if (countKey <= 0 && (an = PrimaryKey.class.getAnnotation(AutoWireByName.class)) != null) {
            String autoWire = ((AutoWireByName) an).value();
            for (Field f : fs) {
                if (f.name.indexOf(autoWire) > 0) {
                    f.importance = Field.PrimaryKey;
                    countKey++;
                    continue;
                }
            }
        }

        // 如果没有Key的话,并且是独立实体的话,设定第一个字段为Key
        if (fs.size() > 0 && type.standalone) {
            if (countKey <= 0) {
                fs.get(0).importance = Field.PrimaryKey;
            }
        }

        return type;
    }

    protected Field readTo(Type resideType, CtField ctField) throws ClassNotFoundException, NotFoundException {
        Object an = null;

        // if (ctField.getName().equals("this$0")) {
        // }

        String name = ctField.getName();

        String displayName = (an = ctField.getAnnotation(DisplayName.class)) != null ? ((DisplayName) an).value()
                : name;

        /* Handle type */
        CtClass fieldTypeClazz = ctField.getType();

        Type fieldType = null;
        boolean array = false;

        /* construct field */
        if (fieldTypeClazz.isArray()) {
            array = true;
            fieldType = types.readData(fieldTypeClazz.getComponentType().getName());
        } else if (fieldTypeClazz.getName().equals(java.util.List.class.getName())
                || fieldTypeClazz.getName().equals(noc.lang.List.class.getName())) {
            // Generic field
            array = true;
            fieldType = types.readData(decorateActualTypeArguments(ctField).get(0));
        } else {
            fieldType = types.readData(fieldTypeClazz.getName());
        }

        if (ctField.hasAnnotation(RealType.class)) {
            fieldType = types.readData(((RealType) ctField.getAnnotation(RealType.class)).value().getName());
        }
        assert fieldType != null;

        Field field = new Field(name, fieldType);
        field.array = array;
        field.displayName = displayName;

        if (fieldType == resideType) {
            field.refer = Field.Cascade;
        } else if (fieldType.master == Type.Scala) {
            field.refer = Field.Scala;
        } else if (check(ctField, fieldType.name, Inline.class)) {
            field.refer = Field.Inline;
        } else {
            field.refer = Field.Reference;
        }
        if (ctField.hasAnnotation(Reference.class))
            field.refer = Field.Reference;

        if (check(ctField, fieldTypeClazz, Important.class))
            field.importance = Field.Important;
        if (check(ctField, fieldTypeClazz, Core.class))
            field.importance = Field.Core;
        if (ctField.hasAnnotation(PrimaryKey.class))
            field.importance = Field.PrimaryKey;

        return field;
    }

    protected boolean check(CtField ctField, String typeName, Class<? extends Annotation> anClz)
            throws ClassNotFoundException, NotFoundException {
        boolean succeed = false;
        Annotation an = null;

        an = anClz.getAnnotation(AutoWireByName.class);
        if (an != null) {
            AutoWireByName au = (AutoWireByName) an;
            if (au.value().indexOf(ctField.getName() + ";") >= 0) {
                succeed = true;
            }
        }

        an = anClz.getAnnotation(AutoWireByType.class);
        if (an != null) {
            AutoWireByType au = (AutoWireByType) an;
            for (Class<?> c : au.value()) {
                if (c.getName().equalsIgnoreCase(typeName)) {
                    succeed = true;
                }
            }
        }

        succeed = ctField.getAnnotation(anClz) != null ? true : succeed;

        return succeed;
    }

    protected boolean check(CtClass ctClass, String typeName, Class<? extends Annotation> anClz)
            throws ClassNotFoundException, NotFoundException {
        boolean succeed = false;
        Annotation an = null;

        an = anClz.getAnnotation(AutoWireByName.class);
        if (an != null) {
            AutoWireByName au = (AutoWireByName) an;
            if (au.value().indexOf(ctClass.getName() + ";") >= 0) {
                succeed = true;
            }
        }

        an = anClz.getAnnotation(AutoWireByType.class);
        if (an != null) {
            AutoWireByType au = (AutoWireByType) an;
            for (Class<?> c : au.value()) {
                if (c.getName().equalsIgnoreCase(typeName)) {
                    succeed = true;
                }
            }
        }

        succeed = ctClass.getAnnotation(anClz) != null ? true : succeed;

        return succeed;
    }

    protected boolean check(CtField ctField, CtClass ctType, Class<? extends Annotation> anClz)
            throws ClassNotFoundException, NotFoundException {
        boolean succeed = false;
        Annotation an = null;

        an = anClz.getAnnotation(AutoWireByName.class);
        if (an != null) {
            AutoWireByName au = (AutoWireByName) an;
            if (au.value().indexOf(ctField.getName() + ";") >= 0) {
                succeed = true;
            }
        }

        an = anClz.getAnnotation(AutoWireByType.class);
        if (an != null) {
            AutoWireByType au = (AutoWireByType) an;
            for (Class<?> c : au.value()) {
                if (c.getName().equalsIgnoreCase(ctType.getName())) {
                    succeed = true;
                }
            }
        }

        succeed = ctType.getAnnotation(anClz) != null ? true : succeed;
        succeed = ctField.getAnnotation(anClz) != null ? true : succeed;

        return succeed;
    }

    protected ArrayList<String> decorateActualTypeArguments(CtField v) {
        SignatureAttribute s = (SignatureAttribute) v.getFieldInfo().getAttribute(SignatureAttribute.tag);
        assert s != null;

        String sig = s.getSignature();
        ArrayList<String> params = new ArrayList<String>();

        int pos = 0;

        assert sig.charAt(pos) == 'L';

        pos++;

        int start = pos;
        while (sig.charAt(++pos) != '<')
            ;

        String typename = sig.substring(start, pos).replace('/', '.');

        assert typename.equals(noc.lang.List.class.getName());
        pos++;

        do {
            String pam;
            if (sig.charAt(pos) != 'L')
                break;

            pos++;

            start = pos;
            while (sig.charAt(++pos) != ';')
                ;

            pam = sig.substring(start, pos).replace('/', '.');
            params.add(pam);
            pos++;
        } while (sig.charAt(pos) != '>');

        return params;
    }
}
