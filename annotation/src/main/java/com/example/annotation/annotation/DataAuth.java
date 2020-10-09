package com.example.annotation.annotation;

import java.lang.annotation.*;

/**
 * @author shen_xi
 * @create 2020/10/09 10:33
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataAuth {
    /**
     * 追加sql的方法名
     *
     * @return
     */

    String method() default "splicingSql";

    /**
     * 表别名
     *
     * @return
     */

    String tableAlias() default "old_sql";

   /**
     * 列名
     *
     * @return
     */

    String columnName() default "org_id";


}
