/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.annotation.annotation.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.annotation.annotation.Query;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked", "all"})
public class QueryHelp {

    public static <T, Q> QueryWrapper<T> createQueryWra(Q query, Class<T> targetClass) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (query == null) {
            return queryWrapper;
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                // 设置对象的访问权限，保证对private的属性的访
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? toSymbolCase(field.getName()) : propName;
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    String value = val.toString();
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        queryWrapper.and(b -> {
                            for (int i = 0; i < blurrys.length; i++) {
                                b.or(i != 0).like(blurrys[i], value);
                            }
                        });
                        continue;
                    }
                    switch (q.type()) {
                        case EQUAL:
                            queryWrapper.eq(attributeName, value);
                            break;
                        case GREATER_THAN:
                            queryWrapper.gt(attributeName, value);
                            break;
                        case LESS_THAN:
                            queryWrapper.lt(attributeName, value);
                            break;
                        case LESS_THAN_EQ:
                            queryWrapper.le(attributeName, value);
                            break;
                        case GREATER_THAN_EQ:
                            queryWrapper.ge(attributeName, value);
                            break;
                        case INNER_LIKE:
                            queryWrapper.like(attributeName, value);
                            break;
                        case LEFT_LIKE:
                            queryWrapper.likeLeft(attributeName, value);
                            break;
                        case RIGHT_LIKE:
                            queryWrapper.likeRight(attributeName, value);
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Object>) val)) {
                                queryWrapper.in(attributeName, (Collection<Object>) val);
                            }
                            break;
                        case NOT_EQUAL:
                            queryWrapper.ne(attributeName, value);
                            break;
                        case NOT_NULL:
                            queryWrapper.isNotNull((Boolean) val, attributeName);
                            break;
                        case IS_NULL:
                            queryWrapper.isNull((Boolean) val, attributeName);
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>) val);
                            queryWrapper.between(attributeName, between.get(0), between.get(1));
                            break;
                        default:
                            break;
                    }
                    switch (q.type()) {
                        case ORDER_BY_ASC:
                            String[] ascColumns = (String[]) val;
                            for (String column : ascColumns) {
                                queryWrapper.orderByAsc(toSymbolCase(column));
                            }
                            break;
                        case ORDER_BY_DESC:
                            String[] dascColumns = (String[]) val;
                            for (String column : dascColumns) {
                                queryWrapper.orderByDesc(toSymbolCase(column));
                            }
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return queryWrapper;
    }

    private static String toSymbolCase(String name) {
        return StrUtil.toSymbolCase(name, '_');

    }

    public static <Q> QueryWrapper<Q> createQueryWra(Q query) {
        return (QueryWrapper<Q>) createQueryWra(query, query.getClass());
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
