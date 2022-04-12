package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("item")
public class Cart {

    @TableId(type = IdType.INPUT)
    private int id;

    @TableField(value = "userid")
    private String userId;

    @TableField(value = "itemid")
    private String itemId;

    @TableField(value = "itemnum")
    private int itemNum;
}
