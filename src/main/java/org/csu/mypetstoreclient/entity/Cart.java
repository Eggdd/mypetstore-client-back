package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@TableName("item")
public class Cart {

    @TableId(type = IdType.INPUT)
    private int id;

    @NotNull(message = "购物车所属用户名不能为空")
    @TableField(value = "userid")
    private String userId;

    @TableField(value = "itemid")
    private String itemId;

    @Min(value = 1, message = "购物车所存放商品不能为负数")
    @TableField(value = "itemnum")
    private int itemNum;
}
