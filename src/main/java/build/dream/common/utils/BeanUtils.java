package build.dream.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ReflectionUtils.makeAccessible(field);
                map.put(field.getName(), ReflectionUtils.getField(field, object));
            }
        }
        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) throws IllegalAccessException, InstantiationException {
        T t = beanClass.newInstance();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            String fieldName = field.getName();
            Object fieldValue = map.get(fieldName);
            if (fieldValue != null) {
                field.set(t, map.get(field.getName()));
            }
        }
        return t;
    }

    public static String beanToXml(Object object, String rootNodeName) throws IllegalAccessException {
        Class<?> objectClass = object.getClass();
        if (StringUtils.isBlank(rootNodeName)) {
            rootNodeName = "xml";
        }
        StringBuffer xml = new StringBuffer("<");
        xml.append(rootNodeName);
        xml.append(">");
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);
            if (fieldValue != null) {
                xml.append("<").append(fieldName).append(">").append("<![CDATA[");
                xml.append(GsonUtils.toJson(fieldValue)).append("]]>").append("</").append(fieldName).append(">");
            }
        }
        xml.append("</").append(rootNodeName);
        return xml.toString();
    }

    public static String mapToXml(Map<String, Object> map, String rootNodeName) {
        if (StringUtils.isBlank(rootNodeName)) {
            rootNodeName = "xml";
        }
        StringBuffer xml = new StringBuffer("<");
        xml.append(rootNodeName);
        xml.append(">");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                xml.append("<").append(key).append(">").append("<![CDATA[");
                xml.append(GsonUtils.toJson(value)).append("]]>").append("</").append(key).append(">");
            }
        }
        xml.append("</").append(rootNodeName);
        return xml.toString();
    }

    public static String generateInsertSql(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder insertSql = new StringBuilder("INSERT INTO ");
        String classSimpleName = clazz.getSimpleName();
        insertSql.append(NamingStrategyUtils.camelCaseToUnderscore(classSimpleName.substring(0, 1).toLowerCase() + classSimpleName.substring(1)));
        insertSql.append("(");
        StringBuilder valuesSql = new StringBuilder(" VALUES(");
        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                    continue;
                }
                String fieldName = field.getName();
                if ("id".equals(fieldName) || "createTime".equals(fieldName) || "lastUpdateTime".equals(fieldName) || "deleted".equals(fieldName)) {
                    continue;
                }
                insertSql.append(NamingStrategyUtils.camelCaseToUnderscore(fieldName));
                insertSql.append(", ");
                valuesSql.append("#{").append(fieldName);
                valuesSql.append("}, ");
            }
            clazz = clazz.getSuperclass();
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.append(")");
        valuesSql.deleteCharAt(valuesSql.length() - 1);
        valuesSql.deleteCharAt(valuesSql.length() - 1);
        valuesSql.append(")");
        insertSql.append(valuesSql);
        return insertSql.toString();
    }

    public static String[] generateInsertAllSql(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder insertSql = new StringBuilder("INSERT INTO ");
        String classSimpleName = clazz.getSimpleName();
        insertSql.append(NamingStrategyUtils.camelCaseToUnderscore(classSimpleName.substring(0, 1).toLowerCase() + classSimpleName.substring(1)));
        insertSql.append("(");
        StringBuilder valuesSql = new StringBuilder("(");
        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                    continue;
                }
                String fieldName = field.getName();
                if ("id".equals(fieldName) || "createTime".equals(fieldName) || "lastUpdateTime".equals(fieldName) || "deleted".equals(fieldName)) {
                    continue;
                }
                insertSql.append(NamingStrategyUtils.camelCaseToUnderscore(fieldName));
                insertSql.append(", ");
                valuesSql.append("#{item.").append(fieldName);
                valuesSql.append("}, ");
            }
            clazz = clazz.getSuperclass();
        }
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.deleteCharAt(insertSql.length() - 1);
        insertSql.append(") VALUES");
        valuesSql.deleteCharAt(valuesSql.length() - 1);
        valuesSql.deleteCharAt(valuesSql.length() - 1);
        valuesSql.append(")");
        return new String[]{insertSql.toString(), valuesSql.toString()};
    }

    public static String generateUpdateSql(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder updateSql = new StringBuilder("UPDATE ");
        String classSimpleName = clazz.getSimpleName();
        updateSql.append(NamingStrategyUtils.camelCaseToUnderscore(classSimpleName.substring(0, 1).toLowerCase() + classSimpleName.substring(1)));
        updateSql.append(" SET ");
        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isNative(modifiers)) {
                    continue;
                }
                String fieldName = field.getName();
                if ("createTime".equals(fieldName) || "lastUpdateTime".equals(fieldName)) {
                    continue;
                }
                updateSql.append(NamingStrategyUtils.camelCaseToUnderscore(fieldName));
                updateSql.append(" = ");
                updateSql.append("#{");
                updateSql.append(fieldName);
                updateSql.append("}, ");
            }
            clazz = clazz.getSuperclass();
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.append(" WHERE id = #{id}");
        return updateSql.toString();
    }
}
