package io.github.springstudent.bean;

/**
 * @author 周宁
 * @Date 2019-03-01 20:31
 */
@FunctionalInterface
public interface ComparePredicate<C>{

    /**
     * @param c1
     * @param c2
     * @return
     */
    boolean compare(C c1, C c2)throws Exception;
}
