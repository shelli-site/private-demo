package com.example.annotation.service;

import cn.hutool.core.util.ArrayUtil;
import com.example.annotation.annotation.DataAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shen_xi
 * @create 2020/10/09 10:59
 */
@Slf4j
public abstract class AbstractDataAuthService {
    /**
     * 默认查询sql,根据角色不同追加不同业务查询条件
     */
    public String splicingSql(String sql, DataAuth action) {
        String tableAlias = action.tableAlias(), columnName = action.columnName(), result;
        if (!StringUtils.isEmpty(tableAlias)) {
            columnName = tableAlias + "." + columnName;
        }
        // 获取权限字段
        List<String> authIds = new ArrayList<>();
        // mock数据
        authIds.add("1");
        authIds.add("2");


        if (authIds.isEmpty()) {
            result = String.format("SELECT %s.* FROM ( %s ) %s WHERE FALSE", tableAlias, sql, tableAlias);
        } else {
            result = String.format("SELECT %s.* FROM ( %s ) %s WHERE %s IN ( %s )",
                    tableAlias, sql, tableAlias, columnName, ArrayUtil.join(authIds.toArray(), ","));
        }
        return result;
    }
}
