package com.example.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * 基本对象的属性名
     **/
    String propName() default "";

    /**
     * 查询方式
     **/
    Type type() default Type.EQUAL;

    /**
     * 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username")
     */
    String blurry() default "";

    enum Type {
        // 相等
        EQUAL
        // 大于等于
        , GREATER_THAN
        // 小于等于
        , LESS_THAN
        // 中模糊查询
        , INNER_LIKE
        // 左模糊查询
        , LEFT_LIKE
        // 右模糊查询
        , RIGHT_LIKE
        // 小于等于
        , LESS_THAN_EQ
        // 大于等于
        , GREATER_THAN_EQ
        // 包含 修饰List
        , IN
        // 不等于
        , NOT_EQUAL
        // between 修饰List
        , BETWEEN
        // 不为空 修饰Boolean
        , NOT_NULL
        // 为空 修饰Boolean
        , IS_NULL
        // 默认升序排列 修饰List
        , ORDER_BY_ASC
        // 默认降序排列 修饰List
        , ORDER_BY_DESC
    }

}
