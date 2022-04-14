package org.csu.mypetstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Category;
import org.csu.mypetstoreclient.entity.Inventory;
import org.csu.mypetstoreclient.entity.Item;
import org.csu.mypetstoreclient.entity.Product;
import org.csu.mypetstoreclient.persistence.CategoryMapper;
import org.csu.mypetstoreclient.persistence.InventoryMapper;
import org.csu.mypetstoreclient.persistence.ItemMapper;
import org.csu.mypetstoreclient.persistence.ProductMapper;
import org.csu.mypetstoreclient.service.CatalogService;
import org.csu.mypetstoreclient.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public CommonResponse<List<Category>> getCategoryList() {

        List<Category> categoryList = categoryMapper.selectList(null);
        if (categoryList.isEmpty()){
            return CommonResponse.createForError("没有分类信息");
        }
        return CommonResponse.createForSuccess(categoryList);
    }

    @Override
    public CommonResponse<Category> getCategory(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);

        if(category == null){
            return CommonResponse.createForError("没有该ID的Category");
        }

        return CommonResponse.createForSuccess(category);
    }

    @Override
    public CommonResponse<Product> getProduct(String productId) {
        Product product = productMapper.selectById(productId);

        if (product == null){
            return CommonResponse.createForError("没有该product的信息");
        }

        return CommonResponse.createForSuccess(product);
    }

    @Override
    public CommonResponse<List<Product>> getProductList() {
        List<Product> productList = productMapper.selectList(null);

        if (productList.isEmpty()){
            return CommonResponse.createForError("没有商品信息");
        }

        return CommonResponse.createForSuccess(productList);
    }

    @Override
    public CommonResponse<List<Product>> getProductListByCategory(String categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",categoryId);
        List<Product> productList = productMapper.selectList(queryWrapper);

        if(productList.isEmpty()){
            return CommonResponse.createForError("该分类下没有Product子分类");
        }

        return CommonResponse.createForSuccess(productList);
    }

    @Override
    public CommonResponse<List<Product>> searchProductList(String keywords) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",keywords);
        List<Product> productList = productMapper.selectList(queryWrapper);

        if (productList.isEmpty()){
            return CommonResponse.createForError("该关键字下没有商品");
        }

        return CommonResponse.createForSuccess(productList);
    }

    @Override
    public CommonResponse<List<ItemVO>> getItemsByProductId(String productId) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid",productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);

        if (itemList.isEmpty()){
            return CommonResponse.createForError("该product下没有item商品");
        }

        Product product = productMapper.selectById(productId);

        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList){
            ItemVO itemVO = itemToItemVO(item,product);
            itemVOList.add(itemVO);
        }

        return CommonResponse.createForSuccess(itemVOList);
    }

    private ItemVO itemToItemVO(Item item,Product product){
        ItemVO itemVO = new ItemVO();

        itemVO.setItemId(item.getItemId());
        itemVO.setProductId(item.getProductId());
        itemVO.setListPrice(item.getListPrice());
        itemVO.setUnitCost(item.getUnitCost());
        itemVO.setSupplierId(item.getSupplierId());
        itemVO.setStatus(item.getStatus());
        itemVO.setAttribute1(item.getAttribute1());
        itemVO.setAttribute2(item.getAttribute2());
        itemVO.setAttribute3(item.getAttribute3());
        itemVO.setAttribute4(item.getAttribute4());

        itemVO.setProduct(product);

        Inventory itemInventory = inventoryMapper.selectById(item.getItemId());
        itemVO.setQuantity(itemInventory.getQuantity());

        return itemVO;
    }

    @Override
    public CommonResponse<ItemVO> getItem(String itemId) {

        Item item = itemMapper.selectById(itemId);

        if (item == null){
            return CommonResponse.createForError("没有该item的信息");
        }

        String productId = item.getProductId();
        Product product = productMapper.selectById(productId);
        ItemVO itemVO = itemToItemVO(item,product);

        return CommonResponse.createForSuccess(itemVO);
    }
}
