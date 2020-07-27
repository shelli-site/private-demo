package com.example.annotation.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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

    @JsonIgnore
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDate createTime;

    @JsonIgnore
    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDate modifyTime;

    @JsonIgnore
    @TableField(value = "USE_STATE")
    @Version
    private String useState;
}