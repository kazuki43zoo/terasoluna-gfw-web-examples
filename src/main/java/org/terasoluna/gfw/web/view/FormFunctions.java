package org.terasoluna.gfw.web.view;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.ClassUtils;

/**
 * EL functions for form object.
 */
public class FormFunctions {

    /**
     * status of present Joda-Time.
     */
    private static final boolean jodaTimePresent = ClassUtils.isPresent("org.joda.time.ReadableInstant",
            FormFunctions.class.getClassLoader());

    /**
     * additional simple value types.
     */
    private static final List<Class<?>> additionalSimpleValueTypes;

    /**
     * initialize class.
     */
    static {
        if (jodaTimePresent) {
            ClassLoader classLoader = FormFunctions.class.getClassLoader();
            Class<?> readableInstantClass = ClassUtils.resolveClassName("org.joda.time.ReadableInstant", classLoader);
            Class<?> readablePartialClass = ClassUtils.resolveClassName("org.joda.time.ReadablePartial", classLoader);
            additionalSimpleValueTypes = Arrays.asList(readableInstantClass, readablePartialClass);
        } else {
            additionalSimpleValueTypes = Collections.emptyList();
        }
    }

    /**
     * Fetch the "active path list" of specified form object.
     * <p>
     * <strong>[about "access path list"]</strong>
     * <ul>
     * <li>active path is access path of spring "form:xxx" tag.</li>
     * <li>active path list is contains all not-null properties.</li>
     * </ul>
     * </p>
     * 
     * @param form
     *            target form object for fetch.
     * @return "active path list" of specified form object.
     */
    public static List<String> getPaths(Object form) {
        if (form == null) {
            return Collections.emptyList();
        }
        List<String> paths = getPaths(null, form);
        return paths;
    }

    /**
     * Fetch the "active path list" of specified object(JavaBean or Simple value
     * object or Collection or Map).
     * 
     * @param basePath
     *            base path of specified object.(case of root object is null)
     * @param object
     *            target object for fetch.
     * @return "active path list" of specified object.
     */
    private static List<String> getPaths(String basePath, Object object) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        TypeDescriptor beanTypeDescriptor = TypeDescriptor.forObject(object);
        List<String> paths = new ArrayList<String>();

        if (beanTypeDescriptor.isCollection()) {
            collectPathsOfCollection(paths, basePath, (Collection<?>) object);

        } else if (beanTypeDescriptor.isArray()) {
            Collection<?> collection = Arrays.asList(beanWrapper.convertIfNecessary(object, Object[].class));
            collectPathsOfCollection(paths, basePath, collection);

        } else if (beanTypeDescriptor.isMap()) {
            collectPathsOfMap(paths, basePath, (Map<?, ?>) object);

        } else {
            collectPathsOfBeanProperties(paths, basePath, beanWrapper);

        }
        return paths;
    }

    /**
     * collect "active path list" of specified bean properties.
     * 
     * @param paths
     *            storing list of active path of in the bean.
     * @param basePath
     *            base path name of specified bean(bean wrapper).
     * @param beanWrapper
     *            wrapper object of specified bean.
     */
    private static void collectPathsOfBeanProperties(List<String> paths, String basePath, BeanWrapper beanWrapper) {
        PropertyDescriptor[] beanPropertyDescriptors = beanWrapper.getPropertyDescriptors();

        for (PropertyDescriptor beanPropertyDescriptor : beanPropertyDescriptors) {
            String propertyName = beanPropertyDescriptor.getName();

            if (!beanWrapper.isReadableProperty(propertyName) || !beanWrapper.isWritableProperty(propertyName)) {
                continue;
            }

            Object value = beanWrapper.getPropertyValue(propertyName);
            if (value == null) {
                continue;
            }

            String path = null;
            if (basePath == null) {
                path = propertyName;
            } else {
                path = basePath + "." + propertyName;
            }

            TypeDescriptor propertyTypeDescriptor = beanWrapper.getPropertyTypeDescriptor(propertyName);
            if (propertyTypeDescriptor.isCollection()) {
                collectPathsOfCollection(paths, path, (Collection<?>) value);

            } else if (propertyTypeDescriptor.isArray()) {
                Collection<?> collection = Arrays.asList(beanWrapper.convertIfNecessary(value, Object[].class));
                collectPathsOfCollection(paths, path, collection);

            } else if (propertyTypeDescriptor.isMap()) {
                collectPathsOfMap(paths, path, (Map<?, ?>) value);

            } else {
                collectPathsOfObject(paths, path, "", value);

            }

        }
    }

    /**
     * collect "active path list" of specified element into collection.
     * 
     * @param paths
     *            storing list of active path of in the specified collection.
     * @param basePath
     *            base path name of specified collection.
     * @param collection
     *            collection object.
     */
    private static void collectPathsOfCollection(List<String> paths, String basePath, Collection<?> collection) {
        int index = 0;
        for (Object elementValue : collection) {
            if (elementValue != null) {
                String pathKey = "[" + index + "]";
                collectPathsOfObject(paths, basePath, pathKey, elementValue);
            }
            index++;
        }
    }

    /**
     * collect "active path list" of specified entry into map.
     * 
     * @param paths
     *            storing list of active path of in the specified map.
     * @param basePath
     *            base path name of specified map.
     * @param map
     *            map object.
     */
    private static void collectPathsOfMap(List<String> paths, String basePath, Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                String pathKey = "[" + entry.getKey() + "]";
                collectPathsOfObject(paths, basePath, pathKey, entry.getValue());
            }
        }
    }

    /**
     * collect "active path list" of specified object.
     * 
     * @param paths
     *            storing list of active path of in the specified object.
     * @param basePath
     *            base path name of specified object.
     * @param pathKey
     *            key's value of in the base path.(collection of array is
     *            "[index]". map is "[key]", simple value is "")
     * @param object
     *            target object.
     */
    private static void collectPathsOfObject(List<String> paths, String basePath, String pathKey, Object object) {
        String path = basePath + pathKey;
        if (isSimpleValueType(object.getClass())) {
            paths.add(path);
        } else {
            List<String> nestedPaths = getPaths(path, object);
            if (!nestedPaths.isEmpty()) {
                paths.addAll(nestedPaths);
            }
        }
    }

    /**
     * Check if the given type represents a "simple" value type: a primitive, a
     * String or other CharSequence, a Number, a Date, a URI, a URL, a Locale or
     * a Class. And if present,Joda Time object.
     * 
     * @param targetClass
     *            the type to check
     * @return whether the given type represents a "simple" value type
     */
    private static boolean isSimpleValueType(Class<?> targetClass) {
        if (BeanUtils.isSimpleValueType(targetClass)) {
            return true;
        }
        if (additionalSimpleValueTypes.isEmpty()) {
            return false;
        }
        for (Class<?> additionalSimpleValueType : additionalSimpleValueTypes) {
            if (additionalSimpleValueType.isAssignableFrom(targetClass)) {
                return true;
            }
        }
        return false;
    }

}
