package com.example.demo.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @Created By ShenXi
 * @Created On 2019/3/27 16:31
 * @Description :
 * @ClassName : User
 */
@Data
@Entity
@Table(name = "AUTH_USER")
public class User {
    @Id
    @Column(length = 32)
    @GenericGenerator(name = "id-generator", strategy = "uuid")
    @GeneratedValue(generator = "id-generator", strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID", dataType = "String", name = "ID", example = "4028839669bf6fe80169bf6ffce90000")
    private String id;

    @NotBlank(message = "姓名不能为空")
    @Column(length = 32)
    @ApiModelProperty(value = "姓名", dataType = "String", name = "name", example = "张三")
    private String name;

    @NotBlank(message = "账号不能为空")
    @Column(length = 32)
    @ApiModelProperty(value = "账号", dataType = "String", name = "account", example = "admin")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Column(length = 64)
    @ApiModelProperty(value = "密码", dataType = "String", name = "password", example = "123456")
    private String password;

    @NotBlank(message = "用户id不能为空")
    @Column(length = 64)
    @ApiModelProperty(value = "用户id", dataType = "String", name = "authId", example = "4028839669bf6fe80169bf6ffce90000")
    private String authId;

}
