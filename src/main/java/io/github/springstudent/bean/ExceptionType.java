package io.github.springstudent.bean;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author 周宁
 * @Date 2018-11-12 12:02
 */
@FunctionalInterface
public interface ExceptionType<T,R> extends Serializable,Function<T,R>{


    /**
     * 获取列名称
     *
     * @param serializable
     * @return String
     */
    static String getLambdaColumnName(Serializable serializable) {
        try {
            Method method = serializable.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(serializable);
            String get = serializedLambda.getImplMethodName();
            return Introspector.decapitalize(get.replace("get", ""));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}