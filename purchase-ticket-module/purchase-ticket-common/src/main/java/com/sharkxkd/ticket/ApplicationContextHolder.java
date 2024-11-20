package com.sharkxkd.ticket;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring容器上下文管理器
 *
 * @author zc
 * @date 2024/11/19 17:11
 **/
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    /**
     * 通过类型从ioc容器获取对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    /**
     * 通过名字从ioc容器获取对象
     */
    public static Object getBean(String name) {
        return CONTEXT.getBean(name);
    }

    /**
     * 通过名字和类型从ioc容器获取对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return CONTEXT.getBean(name, clazz);
    }

    /**
     * 通过类型从ioc容器获取对象数组
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return CONTEXT.getBeansOfType(clazz);
    }

    /**
     * Find whether the bean has annotations.
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return CONTEXT.findAnnotationOnBean(beanName, annotationType);
    }

    /**
     * Get application context.
     */
    public static ApplicationContext getInstance() {
        return CONTEXT;
    }
}
