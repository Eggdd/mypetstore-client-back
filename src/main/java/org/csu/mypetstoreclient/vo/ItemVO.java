package org.csu.mypetstoreclient.vo;

import lombok.Data;
import org.csu.mypetstoreclient.entity.Item;
import org.csu.mypetstoreclient.entity.Product;

import java.math.BigDecimal;

@Data
public class ItemVO {
    //item表中数据
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
    /*//item所属product的属性
    private String categoryId;
    private String name;
    private String description;
*/
    //item的库存，来自inventory表
    private int quantity;

}
