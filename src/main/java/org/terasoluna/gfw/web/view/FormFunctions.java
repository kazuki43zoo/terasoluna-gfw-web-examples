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

public class FormFunctions {

    public static List<String> getPaths(Object form) {
        if (form == null) {
            return Collections.emptyList();
        }
        List<String> paths = getPaths(null, form);
        return paths;
    }

    private static List<String> getPaths(String basePath, Object bean) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        TypeDescriptor beanTypeDescriptor = TypeDescriptor.forObject(bean);
        List<String> paths = new ArrayList<String>();

        if (beanTypeDescriptor.isCollection()) {
            collectPathsOfCollection(paths, basePath, (Collection<?>) bean);

        } else if (beanTypeDescriptor.isArray()) {
            Collection<?> collection = Arrays.asList(beanWrapper.convertIfNecessary(bean, Object[].class));
            collectPathsOfCollection(paths, basePath, collection);

        } else if (beanTypeDescriptor.isMap()) {
            collectPathsOfMap(paths, basePath, (Map<?, ?>) bean);

        } else {
            collectPathsOfBeanProperties(paths, basePath, beanWrapper);

        }
        return paths;
    }

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
                collectPaths(paths, path, "", value);

            }

        }
    }

    private static void collectPathsOfCollection(List<String> paths, String basePath, Collection<?> collection) {
        int index = 0;
        for (Object elementValue : collection) {
            if (elementValue != null) {
                String pathKey = "[" + index + "]";
                collectPaths(paths, basePath, pathKey, elementValue);
            }
            index++;
        }
    }

    private static void collectPathsOfMap(List<String> paths, String basePath, Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                String pathKey = "[" + entry.getKey() + "]";
                collectPaths(paths, basePath, pathKey, entry.getValue());
            }
        }
    }

    private static void collectPaths(List<String> paths, String basePath, String pathKey, Object value) {
        String path = basePath + pathKey;
        if (BeanUtils.isSimpleValueType(value.getClass())) {
            paths.add(path);
        } else {
            List<String> nestedPaths = getPaths(path, value);
            if (!nestedPaths.isEmpty()) {
                paths.addAll(nestedPaths);
            }
        }
    }

}
