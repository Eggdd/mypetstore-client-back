package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inventory")
public class Inventory {
    @TableId(value = "itemid", type = IdType.INPUT)
    private String itemId;
    @TableField(value = "qty")
    private int quantity;
}
