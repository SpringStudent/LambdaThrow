package io.github.springstudent.utils;


import io.github.springstudent.bean.ComparePredicate;
import io.github.springstudent.bean.ContainsPredicate;
import io.github.springstudent.bean.EqualsPredicate;
import io.github.springstudent.bean.ExceptionType;
import io.github.springstudent.exception.*;
import io.github.springstudent.utils.EmptyUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 异常工具类
 *
 * @author 周宁
 * @Date 2019-03-01 11:32
 */
public class ThrowExceptionUtil {
    private static final Predicate PREDICATE_OBJ_NULL = object -> object == null;
    private static final Predicate PREDICATE_OBJ_NOT_NULL = object -> object != null;
    private static final Predicate<List> PREDICATE_COLLECTION_EMPTY = list -> EmptyUtils.isEmpty(list);
    private static final Predicate<List> PREDICATE_COLLECTION_NOT_EMPTY = list -> EmptyUtils.isNotEmpty(list);
    private static final Predicate<String> PREDICATE_STRING_EMPTY = string -> EmptyUtils.isEmpty(string);
    private static final Predicate<String> PREDICATE_STRING_NOT_EMPTY = string -> EmptyUtils.isNotEmpty(string);
    private static final Predicate<Map> PREDICATE_MAP_EMPTY = map -> EmptyUtils.isEmpty(map);
    private static final Predicate<Map> PREDICATE_MAP_NOT_EMPTY = map -> EmptyUtils.isNotEmpty(map);
    private static final Predicate<Object[]> PREDICATE_ARRAY_EMPTY = array -> EmptyUtils.isEmpty(array);
    private static final Predicate<Object[]> PREDICATE_ARRAY_NOT_EMPTY = array -> EmptyUtils.isNotEmpty(array);
    private static final String NULL = "NULL";
    private static final String NOT_NULL = "NOT_NULL";
    private final static MultiKeyMap map = new MultiKeyMap();

    static {
        map.put(Collection.class, NULL, PREDICATE_COLLECTION_EMPTY);
        map.put(String.class, NULL, PREDICATE_STRING_EMPTY);
        map.put(Object.class, NULL, PREDICATE_OBJ_NULL);
        map.put(Map.class, NULL, PREDICATE_MAP_EMPTY);
        map.put(Array.class, NULL, PREDICATE_ARRAY_EMPTY);

        map.put(Collection.class, NOT_NULL, PREDICATE_COLLECTION_NOT_EMPTY);
        map.put(String.class, NOT_NULL, PREDICATE_STRING_NOT_EMPTY);
        map.put(Object.class, NOT_NULL, PREDICATE_OBJ_NOT_NULL);
        map.put(Map.class, NOT_NULL, PREDICATE_MAP_NOT_EMPTY);
        map.put(Array.class, NOT_NULL, PREDICATE_ARRAY_NOT_EMPTY);
    }

    /**
     * 校验参数异常
     *
     * @param obj          受检对象
     * @param type Object::attribute，用于获取对象属性上的@ApiModelProperty注解的value方法返回值
     * @throws ParamInvalidException
     */
    public static <T, R> void validAndThrowParamInvalidException(Object obj, ExceptionType<T, R> type) throws ApplicationException {
        validAndThrowException(obj, null, null, ParamInvalidException.class, type);
    }

    /**
     * @param t       受检对象
     * @param predicate 异常条件
     * @param desc      抛出异常描述
     * @throws ApplicationException
     */
    public static <T> void validAndThrowParamInvalidException(T t, Predicate<T> predicate, String desc) throws ApplicationException {
        validAndThrowException(t, predicate, desc, ParamInvalidException.class, null);
    }


    public static void validAndThrowParamInvalidException(Object obj, String desc) throws ApplicationException {
        validAndThrowException(obj, choosePredicate(obj, NULL), desc, ParamInvalidException.class, null);
    }

    public static <T> void compareAndThrowParamInvalidException(T t1, T t2, ComparePredicate<T> comparePredicate, String desc) throws Exception {
        if (comparePredicate.compare(t1, t2)) {
            throw newExceptionByEClss(ParamInvalidException.class, desc);
        }
    }

    public static <T> void equalsAndThrowParamInvalidException(T t1, T t2, EqualsPredicate<T> equalsPredicate, String desc) throws Exception {
        if (equalsPredicate.equals(t1, t2)) {
            throw newExceptionByEClss(ParamInvalidException.class, desc);
        }
    }

    public static <P, C> void containsAndThrowParamInvalidException(P p, C c, ContainsPredicate<P, C> containsPredicate, String desc) throws Exception {
        if (containsPredicate.contains(p, c)) {
            throw newExceptionByEClss(ParamInvalidException.class, desc);
        }
    }

    public static <T> void validAndThrowNoPermissionException(T t, Predicate<T> predicate, String desc) throws ApplicationException {
        validAndThrowException(t, predicate, desc, NoPermissionException.class, null);
    }

    public static <T> void compareAndThrowNoPermissionException(T t1, T t2, ComparePredicate<T> comparePredicate, String desc) throws Exception {
        if (comparePredicate.compare(t1, t2)) {
            throw newExceptionByEClss(NoPermissionException.class, desc);
        }
    }

    public static <T> void equalsAndThrowNoPermissionException(T t1, T t2, EqualsPredicate<T> equalsPredicate, String desc) throws Exception {
        if (equalsPredicate.equals(t1, t2)) {
            throw newExceptionByEClss(NoPermissionException.class, desc);
        }
    }

    public static <P, C> void containsAndThrowNoPermissionException(P p, C c, ContainsPredicate<P, C> containsPredicate, String desc) throws Exception {
        if (containsPredicate.contains(p, c)) {
            throw newExceptionByEClss(NoPermissionException.class, desc);
        }
    }


    public static <T> void validAndThrowResultException(T t, Predicate<T> predicate, String desc) throws ApplicationException {
        validAndThrowException(t, predicate, desc, ResultException.class, null);
    }

    public static <T> void compareAndThrowResultException(T t1, T t2, ComparePredicate<T> comparePredicate, String desc) throws Exception {
        if (comparePredicate.compare(t1, t2)) {
            throw newExceptionByEClss(ResultException.class, desc);
        }
    }

    public static <T> void equalsAndThrowResultException(T t1, T t2, EqualsPredicate<T> equalsPredicate, String desc) throws Exception {
        if (equalsPredicate.equals(t1, t2)) {
            throw newExceptionByEClss(ResultException.class, desc);
        }
    }

    public static <P, C> void containsAndThrowResultException(P p, C c, ContainsPredicate<P, C> containsPredicate, String desc) throws Exception {
        if (containsPredicate.contains(p, c)) {
            throw newExceptionByEClss(ResultException.class, desc);
        }
    }

    public static <T, E extends ApplicationException> void validAndThrowExceptionByClass(T t, Predicate<T> predicate, String desc, Class<E> eClass) throws ApplicationException {
        validAndThrowException(t, predicate, desc, eClass, null);
    }

    public static <T, E extends ApplicationException> void compareAndThrowExceptionByClass(T t1, T t2, ComparePredicate<T> comparePredicate, String desc, Class<E> eClass) throws Exception {
        if (comparePredicate.compare(t1, t2)) {
            throw newExceptionByEClss(eClass, desc);
        }
    }

    public static <T, E extends ApplicationException> void equalsAndThrowExceptionByClass(T t1, T t2, EqualsPredicate<T> equalsPredicate, String desc, Class<E> eClass) throws Exception {
        if (equalsPredicate.equals(t1, t2)) {
            throw newExceptionByEClss(eClass, desc);
        }
    }

    public static <P, C, E extends ApplicationException> void containsAndThrowExceptionByClass(P p, C c, ContainsPredicate<P, C> containsPredicate, String desc, Class<E> eClass) throws Exception {
        if (containsPredicate.contains(p, c)) {
            throw newExceptionByEClss(eClass, desc);
        }
    }

    /**
     * 数据不存在异常
     *
     * @param obj  受检对象
     * @param desc 抛出异常描述
     * @throws ApplicationException
     */
    public static void validAndThrowDataNotFoundException(Object obj, String desc) throws ApplicationException {
        validAndThrowException(obj, choosePredicate(obj, NULL), desc, DataNotFoundException.class, null);
    }

    /**
     * 数据已存在异常
     *
     * @param obj  受检对象
     * @param desc 抛出异常描述
     * @throws ApplicationException
     */
    public static void validAndThrowAlreadyExistException(Object obj, String desc) throws ApplicationException {
        validAndThrowException(obj, choosePredicate(obj, NOT_NULL), desc, AlreadyExistException.class, null);
    }

    private static <T, R, E extends ApplicationException> void validAndThrowException(Object obj, Predicate predicate, String desc, Class<E> eClass, ExceptionType<T, R> type) throws ApplicationException {
        ApplicationException exception = null;
        if (null != type) {
            try {
                Method method = type.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                SerializedLambda serializedLambda = (SerializedLambda) method.invoke(type);
                Class cls = Class.forName(serializedLambda.getImplClass().replace("/", "."));
                String getter = serializedLambda.getImplMethodName();
                String fieldName = Introspector.decapitalize(getter.replace("get", ""));
                Field[] fields = cls.getDeclaredFields();
                for (Field f : fields) {
                    ApiModelProperty anno = f.getDeclaredAnnotation(ApiModelProperty.class);
                    if (anno != null && fieldName.equals(f.getName())) {
                        if (obj instanceof String) {
                            if (EmptyUtils.isEmpty((String) obj)) {
                                exception = newExceptionByEClss(eClass, anno.value() + Optional.ofNullable(desc).orElse("不能为空或者null"));
                            }
                        } else if (obj instanceof Collection) {
                            if (EmptyUtils.isEmpty((Collection) obj)) {
                                exception = newExceptionByEClss(eClass, anno.value() + Optional.ofNullable(desc).orElse("不能为空或者null"));
                            }
                        } else if (obj instanceof Map) {
                            if (EmptyUtils.isEmpty((Map) obj)) {
                                exception = newExceptionByEClss(eClass, anno.value() + Optional.ofNullable(desc).orElse("不能为空或者null"));
                            }
                        } else if (obj != null && obj.getClass().isArray()) {
                            if (EmptyUtils.isEmpty((Object[]) obj)) {
                                exception = newExceptionByEClss(eClass, anno.value() + Optional.ofNullable(desc).orElse("不能为空或者null"));
                            }
                        } else {
                            if (null == obj) {
                                exception = newExceptionByEClss(eClass, anno.value() + Optional.ofNullable(desc).orElse("不能为null"));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != exception) {
                    throw exception;
                }
            }
        } else {
            if (predicate.test(obj)) {
                throw newExceptionByEClss(eClass, desc);
            }

        }

    }


    /**
     * 根据elcss类型创建异常对象
     *
     * @param eClss
     * @param message
     * @param <E>
     * @return ApplicationException
     */
    private static <E extends ApplicationException> ApplicationException newExceptionByEClss(Class<E> eClss, String message) {
        if (ParamInvalidException.class.equals(eClss)) {
            return new ParamInvalidException(message);
        }
        if (DataNotFoundException.class.equals(eClss)) {
            return new DataNotFoundException(message);
        }
        if (AlreadyExistException.class.equals(eClss)) {
            return new AlreadyExistException(message);
        }
        if (ResultException.class.equals(eClss)) {
            return new ResultException(message);
        }
        if (NoPermissionException.class.equals(eClss)) {
            return new NoPermissionException(message);
        }
        try {
            Constructor<?> cons = eClss.getConstructor(String.class);
            return (ApplicationException) cons.newInstance(message);
        } catch (Exception e) {
            return new ApplicationException(message);
        }
    }

    /**
     * 根据对象类型选择一个predicate
     *
     * @param obj
     * @param keyType
     * @return Predicate
     */
    private static Predicate choosePredicate(Object obj, String keyType) {
        if (obj instanceof Collection) {
            return (Predicate) map.get(Collection.class, keyType);
        } else if (obj instanceof String) {
            return (Predicate) map.get(String.class, keyType);
        } else if (obj instanceof Map) {
            return (Predicate) map.get(Map.class, keyType);
        } else if (obj != null && obj.getClass().isArray()) {
            return (Predicate) map.get(Array.class, keyType);
        } else {
            return (Predicate) map.get(Object.class, keyType);
        }
    }

}
