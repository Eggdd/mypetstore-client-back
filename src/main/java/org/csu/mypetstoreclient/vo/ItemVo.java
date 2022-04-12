package org.csu.mypetstoreclient.vo;

import lombok.Data;
import org.csu.mypetstoreclient.entity.Product;

import java.math.BigDecimal;

@Data
public class ItemVo {
    private String itemId;
    private String productId;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private int supplierId;
    private String status;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private Product product;
    private int quantity;

}
