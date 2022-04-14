package org.csu.mypetstoreclient.vo;

import lombok.Data;
import org.csu.mypetstoreclient.entity.Item;

import java.math.BigDecimal;

@Data
public class LineItemVO {
    private int orderId;
    private int lineNumber;
    private String itemId;
    private int quantity;
    private BigDecimal unitPrice; // item的listPrice
    private ItemVO item;
    private BigDecimal total; // quantity*unitPrice
}
