package io.github.springstudent.bean;

/**
 * @author 周宁
 * @Date 2019-03-01 20:25
 */
@FunctionalInterface
public interface ContainsPredicate<P,C>{
    /**
     * 父包含子
     * @param p 父
     * @param c 子
     * @return
     */
    boolean contains(P p, C c)throws Exception;
}
