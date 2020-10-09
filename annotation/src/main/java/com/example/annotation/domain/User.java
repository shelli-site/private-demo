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
@TableName(value = "user")
public class User {
    @TableId(value = "use_id", type = IdType.AUTO)
    private Integer useId;

    @TableField(value = "use_name")
    private String useName;

    @TableField(value = "use_sex")
    private String useSex;

    @TableField(value = "use_age")
    private Integer useAge;

    @TableField(value = "use_id_no")
    private String useIdNo;

    @TableField(value = "use_phone_num")
    private String usePhoneNum;

    @TableField(value = "use_email")
    private String useEmail;

    @JsonIgnore
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDate createTime;

    @JsonIgnore
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDate modifyTime;

    @JsonIgnore
    @TableField(value = "use_state")
    @Version
    private String useState;
}