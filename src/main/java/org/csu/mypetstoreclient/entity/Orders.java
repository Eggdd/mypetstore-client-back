package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class Orders {

    @TableId(value = "orderid" , type = IdType.INPUT)
    private int orderId;

    @TableField(value = "userid")
    private String username;

    @NotNull(message = "订单日期不能为空")
    @TableField(value = "orderdate")
    private Date orderDate;

    @NotNull(message = "shipAddress1不能为空")
    @TableField(value = "shipaddr1")
    private String shipAddress1;

    @TableField(value = "shipaddr2")
    private String shipAddress2;

    @NotNull(message = "shipCity不能为空")
    @TableField(value = "shipcity")
    private String shipCity;

    @NotNull(message = "shipState不能为空")
    @TableField(value = "shipstate")
    private String shipState;

    @NotNull(message = "shipZip不能为空")
    @TableField(value = "shipzip")
    private String shipZip;

    @NotNull(message = "shipCountry不能为空")
    @TableField(value = "shipcountry")
    private String shipCountry;

    @NotNull(message = "billAddress1不能为空")
    @TableField(value = "billaddr1")
    private String billAddress1;

    @NotNull(message = "billAddress2不能为空")
    @TableField(value = "billaddr2")
    private String billAddress2;

    @NotNull(message = "billCity不能为空")
    @TableField(value = "billcity")
    private String billCity;

    @NotNull(message = "billState不能为空")
    @TableField(value = "billstate")
    private String billState;

    @NotNull(message = "billZip不能为空")
    @TableField(value = "billzip")
    private String billZip;

    @NotNull(message = "billCountry不能为空")
    @TableField(value = "billcountry")
    private String billCountry;

    @NotNull(message = "courier不能为空")
    private String courier;

    @TableField(value = "totalprice")
    private BigDecimal totalPrice;

    @NotNull(message = "billToFirstName不能为空")
    @TableField(value = "billtofirstname")
    private String billToFirstName;

    @NotNull(message = "billToLastName不能为空")
    @TableField(value = "billtolastname")
    private String billToLastName;

    @NotNull(message = "shipToFirstName不能为空")
    @TableField(value = "shiptofirstname")
    private String shipToFirstName;

    @NotNull(message = "shipToLastName不能为空")
    @TableField(value = "shiptolastname")
    private String shipToLastName;

    @NotNull(message = "creditCard不能为空")
    @TableField(value = "creditcard")
    private String creditCard;

    @NotNull(message = "expiryDate不能为空")
    @TableField(value = "exprdate")
    private String expiryDate;

    @NotNull(message = "cardType不能为空")
    @TableField(value = "cardtype")
    private String cardType;

    private String locale;

}
