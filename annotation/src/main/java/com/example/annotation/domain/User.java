package com.example.annotation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName(value = "USER")
public class User {
    @TableId(value = "USE_ID", type = IdType.AUTO)
    private Integer useId;

    @TableField(value = "USE_NAME")
    private String useName;

    @TableField(value = "USE_SEX")
    private String useSex;

    @TableField(value = "USE_AGE")
    private Integer useAge;

    @TableField(value = "USE_ID_NO")
    private String useIdNo;

    @TableField(value = "USE_PHONE_NUM")
    private String usePhoneNum;

    @TableField(value = "USE_EMAIL")
    private String useEmail;

    @TableField(value = "CREATE_TIME")
    private LocalDate createTime;

    @TableField(value = "MODIFY_TIME")
    private LocalDate modifyTime;

    @TableField(value = "USE_STATE")
    private String useState;
}