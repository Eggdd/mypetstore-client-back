package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@TableName("account")
public class Account {

    @NotNull(message = "用户名不能为空")
    @TableId(value = "userid", type = IdType.INPUT)
    private String username;

    private String email;

    @TableField(value = "firstname")
    private String firstName;

    @TableField(value = "lastname")
    private String lastName;

    private String status;

    @TableField(value = "addr1")
    private String address1;

    @TableField(value = "addr2")
    private String address2;

    private String city;
    private String state;
    private String zip;
    private String country;

    @NotEmpty(message = "电话号码不能为空")
    private String phone;

}
