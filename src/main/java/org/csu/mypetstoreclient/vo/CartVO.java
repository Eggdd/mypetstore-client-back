package org.csu.mypetstoreclient.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartVO {


    private ItemVO item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;
}
