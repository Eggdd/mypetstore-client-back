package org.csu.mypetstoreclient.controller;

import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.entity.Product;
import org.csu.mypetstoreclient.service.CatalogService;
import org.csu.mypetstoreclient.vo.ItemVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.entity.Product;
import org.csu.mypetstoreclient.service.CatalogService;
import org.csu.mypetstoreclient.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalog/")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    //1.  获得所有category类信息
    @GetMapping("categories")
    @ResponseBody
    public CommonResponse<List<Category>> getCategoryList(){
        return catalogService.getCategoryList();
    }

    //2.  根据id获得单个category类信息
    @GetMapping("categories/{id}")
    @ResponseBody
    public CommonResponse<Category> getCategory(@PathVariable("id") String categoryId){
        return catalogService.getCategory(categoryId);
    }

    //3.  获得某个catagory下的所有product
    @GetMapping("categories/{id}/products")
    @ResponseBody
    public CommonResponse<List<Product>> getProductListByCategory(@PathVariable("id") String categoryId){
        return catalogService.getProductListByCategory(categoryId);
    }

    //4.  获得所有的product
    @GetMapping("products")
    @ResponseBody
    public CommonResponse<List<Product>> getProductList(){
        return catalogService.getProductList();
    }

    //5. 根据关键字获得product
    @GetMapping("products/keywords")
    @ResponseBody
    public CommonResponse<List<Product>> searchProductList(@RequestParam String keywords){
        return catalogService.searchProductList("%" + keywords.toLowerCase() + "%");
    }

    //6. 根据id获得单个product
    @GetMapping("products/{id}")
    @ResponseBody
    public CommonResponse<Product> getProduct(
            @PathVariable("id") String productId){
        return catalogService.getProduct(productId);
    }

    //7.  获得某个product下的所有item
    @GetMapping("products/{id}/items")
    @ResponseBody
    public CommonResponse<List<ItemVO>> getItemsByProductId(@PathVariable("id") String productId){
        return catalogService.getItemsByProductId(productId);
    }

    //8.  根据id获得单个item
    @GetMapping("items/{id}")
    @ResponseBody
    public CommonResponse<ItemVO> getItem(@PathVariable("id") String itemId){
        return catalogService.getItem(itemId);
    }

}

