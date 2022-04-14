package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orderstatus")
public class OrderStatus {

    @TableId(value = "orderid" , type = IdType.INPUT)
    private int orderId;

    @TableField(value = "linenum")
    private int lineNum;

    @TableField(value = "timestamp")
    private Date timeStamp;

    private String status;
}
