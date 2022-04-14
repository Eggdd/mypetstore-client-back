package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName("signon")
public class SignOn {

    @TableId(type = IdType.INPUT)
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
