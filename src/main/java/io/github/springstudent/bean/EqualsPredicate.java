package io.github.springstudent.bean;

/**
 * @author 周宁
 * @Date 2019-03-01 20:28
 */
@FunctionalInterface
public interface EqualsPredicate<T>{
    /**
     * @param t1
     * @param t2
     * @return
     */
    boolean equals(T t1, T t2)throws Exception;
}
