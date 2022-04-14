package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@TableName("lineItem")
public class LineItem {

    @TableId(value = "orderid" , type = IdType.INPUT)
    private int orderId;

    @TableField(value = "linenum")
    private int lineNumber;

    @Min(value = 1, message = "订单所购买商品数不能小于1")
    private int quantity;

    @TableField(value = "itemid")
    private String itemId;

    @TableField(value = "unitprice")
    private BigDecimal unitPrice;
}
