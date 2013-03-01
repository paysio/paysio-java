package com.paysio.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

    private static Map<Class<?>, PropertyDescriptor[]> pdMap = new HashMap<Class<?>, PropertyDescriptor[]>();

    private static class PropertyDescriptor {

        private String name;
        private Class<?> type;
        private Method readMethod = null;
        private Method writeMethod = null;

        PropertyDescriptor(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }

        public Class<?> getType() {
            return type;
        }

        public boolean isReadable() {
            return readMethod != null;
        }

        public boolean isWritable() {
            return writeMethod != null;
        }

        public Method getReadMethod() {
            return readMethod;
        }

        public Method getWriteMethod() {
            return writeMethod;
        }

        public void setWriteMethod(Method m) {
            writeMethod = m;
        }

        public void setReadMethod(Method m) {
            readMethod = m;
        }
    }

    private synchronized static PropertyDescriptor[] getPublicProperties(Class<?> clazz) {
        PropertyDescriptor[] descriptors = pdMap.get(clazz);
        if (descriptors == null) {
            Map<String, PropertyDescriptor> descriptorsMap = new HashMap<String, PropertyDescriptor>();
            Method[] publicMethods = clazz.getMethods();
            for (Method m : publicMethods) {
                int modifiers = m.getModifiers();
                Class<?>[] paramTypes = m.getParameterTypes();
                if (Modifier.isPublic(modifiers)
                        && !Modifier.isStatic(modifiers)
                        && !m.isSynthetic()
                        && !m.isBridge()) {

                    String name = m.getName();
                    Class<?> type;
                    Class<?> returnType = m.getReturnType();
                    boolean isSetter = false;
                    if (name.startsWith("set") && (paramTypes.length == 1)
                            && (void.class.equals(returnType))) {
                        name = name.substring(3);
                        type = paramTypes[0];
                        isSetter = true;
                    } else if (name.startsWith("get") && (paramTypes.length == 0)) {
                        name = name.substring(3);
                        type = returnType;
                    } else if (name.startsWith("is")
                            && (paramTypes.length == 0)
                            && (boolean.class.equals(returnType) || Boolean.class
                                    .equals(returnType))) {
                        name = name.substring(2);
                        type = returnType;
                    } else {
                        continue;
                    }

                    if ((name.length() > 0) && Character.isUpperCase(name.charAt(0))) {
                        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                        PropertyDescriptor pd = descriptorsMap.get(name);
                        if (pd == null) {
                            pd = new PropertyDescriptor(name, type);
                            descriptorsMap.put(name, pd);
                        } else if (!type.equals(pd.getType())) {
                            // type of this property access method conflicts with another
                            // ignoring
                            continue;
                        }

                        if (isSetter) {
                            pd.setWriteMethod(m);
                        } else {
                            pd.setReadMethod(m);
                        }
                    }

                }
            }
            descriptors = descriptorsMap.values().toArray(
                    new PropertyDescriptor[descriptorsMap.size()]);
            pdMap.put(clazz, descriptors);
        }
        return descriptors;
    }

    public static void copyProperties(Object target, Object source) throws IllegalAccessException,
            InvocationTargetException {
        Class<?> clazz = source.getClass();

        if (target.getClass() != clazz) {
            throw new RuntimeException("Incompartible types!");
        }

        PropertyDescriptor[] descriptors = BeanUtils.getPublicProperties(clazz);
        for (PropertyDescriptor pd : descriptors) {
            if (pd.isWritable() && pd.isReadable()) {
                Method setter = pd.getWriteMethod();
                Method getter = pd.getReadMethod();
                setter.invoke(target, getter.invoke(source, (Object[]) null));
            }
        }
    }
}
