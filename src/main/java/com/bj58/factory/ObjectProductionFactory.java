package com.bj58.factory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fei.gao on 2017/5/11.
 * create Object for Method return Type
 * 为方法生成默认的返回对象
 */
public class ObjectProductionFactory {

    /** 保存  <classMethod,托底json数据>*/
    private static Map<String, String> sourceMap = new ConcurrentHashMap<String, String>(1000);

    /** 保存 <classMethod,方法返回的值对象 (托底数据转化为该方法的托底Object)> */
    private static Map<String, Object> instanceMap = new ConcurrentHashMap<String, Object>(1000);

    /**保存 <classMethod,classType (方法返回值的类型)> */
    private static Map<String, String> listTypeMap = new ConcurrentHashMap<String, String>(1000);

    /**
     * add 托底 json数据
     * @param classMethod
     * @param source
     * @param classType
     * @return
     */
    public static boolean addJsonRet(String classMethod, String source, String classType) {
        if (classMethod == null || source == null) {
            return false;
        }
        instanceMap.remove(classMethod);
        if(null != classType && !"".equals(classType))
            listTypeMap.put(classMethod, classType);
        return sourceMap.put(classMethod, source) != null;
    }

    /**
     * 获取方法返回的托底 对象
     * @param classMethod
     * @param retClass
     * @return
     */
    public static Object getRetInstance(String classMethod, Class retClass) {
        String source = sourceMap.get(classMethod);
        if (classMethod == null || source == null) {
            return null;
        }
        if (instanceMap.containsKey(classMethod)) {
            return instanceMap.get(classMethod);
        }

        Object ret = null;
        if (retClass.isPrimitive() || retClass.isAssignableFrom(String.class)) {
            JSONObject jsonObject = JSONObject.fromObject(source);
            return jsonObject.get("ret");
        }
        if (retClass.isArray() || retClass.isAssignableFrom(List.class)) {
            JSONArray jsonArray = JSONArray.fromObject(source);
            Class type = null;
            try {
                type = Class.forName(listTypeMap.get(classMethod));
            } catch (ClassNotFoundException e) {
            }
            ret = JSONArray.toCollection(jsonArray, type);
        } else {
            JSONObject jsonObject = JSONObject.fromObject(source);
            ret = (Object) JSONObject.toBean(jsonObject, retClass);
        }
        instanceMap.put(classMethod, ret);
        return ret;
    }


    public static boolean remove(String classMethod) {
        instanceMap.remove(classMethod);
        return sourceMap.remove(classMethod) != null;
    }

}
