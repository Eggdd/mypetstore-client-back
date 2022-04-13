package org.csu.mypetstoreclient.service;

import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.entity.Product;
import org.csu.mypetstoreclient.vo.ItemVO;

import java.util.List;

public interface CatalogService {
    CommonResponse<List<Category>> getCategoryList();

    CommonResponse<Category> getCategory(String categoryId);

    CommonResponse<Product> getProduct(String productId);

    CommonResponse<List<Product>> getProductList();

    CommonResponse<List<Product>> getProductListByCategory(String categoryId);

    CommonResponse<List<Product>> searchProductList(String keywords);

    CommonResponse<List<ItemVO>> getItemsByProductId(String productId);

    CommonResponse<ItemVO> getItem(String itemId);
}
