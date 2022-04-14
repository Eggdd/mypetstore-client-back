package org.csu.mypetstoreclient.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineItemVO {
    private int orderId;
    private int lineNumber;
    private String itemId;
    private int quantity;
    private BigDecimal unitPrice; // item的listPrice
    private ItemDetailsVO item;
    private BigDecimal total; // quantity*unitPrice
}
