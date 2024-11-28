package com.sharkxkd.ticket.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Bean转换工具类
 * @author zc
 * @date 2024/11/21 20:53
 **/
@Slf4j
public class BeanUtil {
    /**
     * 使用泛型进行属性复制，并允许自定义字段值处理逻辑
     * @param source 源对象
     * @param target 目标对象
     * @param <T> 源对象类型
     * @param <R> 目标对象类型
     */
    private static <T, R> void copyProperties(T source, R target) {
        if (source == null || target == null) {
            return;
        }

        // 获取源对象和目标对象的类
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // 获取源对象和目标对象的字段（属性）
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            // 设置源字段可访问
            sourceField.setAccessible(true);

            for (Field targetField : targetFields) {
                // 设置目标字段可访问
                targetField.setAccessible(true);

                // 匹配字段名称和类型
                if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {


                    try {
                        // 获取源字段值
                        Object sourceValue = sourceField.get(source);
                        targetField.set(target,sourceValue);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 属性过滤器的默认实现
     * @return  返回一个lambda表达式
     */
    private static BiFunction<Field,Object,Object> defaultFiledMapper(){
        return (value,filed) -> value;
    }

    private static <T> T buildInstance(Class<T> targetClass){
        try{
            Constructor<T> constructor = targetClass.getConstructor();
            return constructor.newInstance();
        }catch (NoSuchMethodException e){
            log.error("There is no NoArgsConstructor for {},please add @NoArgsConstructor to it",targetClass);
            log.error("error stack",e);
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
            log.error("");
            log.error("error stack",e);
        }
        return null;
    }

    /**
     * 将同名同类型属性一一转换
     * @param source    源对象
     * @param targetClass    目标对象
     * @return          返回目标对象
     * @param <S>       源对象类型
     * @param <T>       目标对象类型
     */
    public static <S,T> T convert(S source,Class<T> targetClass){
        T targetInstance = buildInstance(targetClass);
        copyProperties(source,targetInstance);
        return targetInstance;
    }

    /**
     * 将同名同类型属性一一转换
     * @param sources   源对象列表
     * @param target    目标对象
     * @return          返回目标对象
     * @param <S>       源对象类型
     * @param <T>       目标对象类型
     */
    public static <S,T> List<T> convert(List<S> sources, Class<T> target){
        List<T> targetList = new ArrayList<>();
        sources.forEach(
                source -> targetList.add(convert(source, target))
        );
        return targetList;
    }
}
