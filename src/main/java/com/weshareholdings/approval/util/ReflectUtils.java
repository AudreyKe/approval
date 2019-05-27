package com.weshareholdings.approval.util;

/**
 * @Author liling
 * @Date 2018/12/28
 **/
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

public class ReflectUtils {
    /**
     * Description: 获取修改内容
     */
    public static Map<String, Object> packageModifyContent(Object source, Object target) {
        Map<String,Object> result = new HashMap<>();
        StringBuffer modifyContent = new StringBuffer();
        if(null == source || null == target) {
            return result;
        }
        //取出source类
        Class<?> sourceClass = source.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        for(Field srcField : sourceFields) {
            String srcName = srcField.getName();
            // 排除id检测
            if(srcName.equals("id")){
                continue;
            }
            //获取srcField值
            String srcValue = getFieldValue(source, srcName) == null ? "" : getFieldValue(source, srcName).toString();
            //获取对应的targetField值
            String targetValue = getFieldValue(target, srcName) == null ? "" : getFieldValue(target, srcName).toString();

            if(StringUtils.isEmpty(srcValue) && StringUtils.isEmpty(targetValue)) {
                continue;
            }
            // 处理list类型数据
            if (srcField.getType().getName().equals("java.util.List")){
                Set<Object> targetMapSet = new HashSet<>(getFieldValue(target, srcName) == null ? new ArrayList<>() : (List)getFieldValue(target, srcName));
                Set<Object> targetSet = new HashSet<>();
                for (Object object : targetMapSet){
                    Object map = transBean2Map(object);
                    targetSet.add(map);
                }
                Set<Object> sourceSet = new HashSet<>(getFieldValue(source, srcName) == null ? new ArrayList<>() : (List)getFieldValue(source, srcName));
                    targetSet.retainAll(sourceSet);
                    sourceSet.removeAll(targetSet);
                    if (sourceSet != null && sourceSet.size() > 0){
                        result.put(srcName, srcName + "由‘" + targetValue + "’修改为‘" + srcValue + "’;");
                    }
            }else{
                if(!srcValue.equals(targetValue)) {
                    modifyContent.append(srcName + "由‘" + targetValue + "’修改为‘" + srcValue + "’;");
                    result.put("modifyContent", modifyContent.toString());
                }
            }
        }
        return result;
    }

    /**
     *  获取修改内容
     * source 当前内容
     * target 比对内容
     * comparedProperties 比较属性
     */
    public static String packageModifyContent(Object source, Object target, Map<String,String> comparedProperties) {
        StringBuffer modifyContent = new StringBuffer();
        if(null == source || null == target) {
            return "";
        }
        //取出source类
        Class<?> sourceClass = source.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        for(Field srcField : sourceFields) {
            String srcName = srcField.getName();
            //获取此属性值。条件: 1 比较所有属性; 2 比较的属性在比较集合中
            if(null == comparedProperties || (null != comparedProperties && comparedProperties.containsKey(srcName))) {

                //获取srcField值
                String srcValue = getFieldValue(source, srcName) == null ? "" : getFieldValue(source, srcName).toString();
                //获取对应的targetField值
                Class<?> type = srcField.getType();
                String targetValue = getFieldValue(target, srcName) == null ? "" : getFieldValue(target, srcName).toString();
                if(StringUtils.isEmpty(srcValue) && StringUtils.isEmpty(targetValue)) {
                    continue;
                }
                if(!srcValue.equals(targetValue)) {
                    modifyContent.append(comparedProperties.get(srcName) + "由‘" + targetValue + "’修改为‘" + srcValue + "’;");
                }
            }
        }
        return modifyContent.toString();
    }

    /**
     * Description: 获取Obj对象的fieldName属性的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object fieldValue = null;
        if(null == obj) {
            return null;
        }
//        Method[] methods = obj.getClass().getDeclaredMethods();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if(!methodName.startsWith("get")) {
                continue;
            }
            if(methodName.startsWith("get") && methodName.substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                try {
                    fieldValue = method.invoke(obj, new Object[] {});
                } catch (Exception e) {
                    System.out.println("取值出错，方法名 " + methodName);
                    continue;
                }
            }
        }
        return fieldValue;
    }


    public static Object transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return obj;
        }
        try {
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
            return obj;
        }
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}

