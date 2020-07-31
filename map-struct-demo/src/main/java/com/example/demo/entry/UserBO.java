package com.example.demo.entry;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created By shelli On 2020/4/27 15:01
 */
@Data
@Accessors(chain = true)
public class UserBO {
    /**
     * 用户编号
     **/
    private Integer id;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
}
