package org.csu.mypetstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.*;
import org.csu.mypetstoreclient.persistence.*;
import org.csu.mypetstoreclient.service.CartService;
import org.csu.mypetstoreclient.vo.CartVO;
import org.csu.mypetstoreclient.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CommonResponse addCart(String username,String itemId) {

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",username);
        Account account = accountMapper.selectOne(queryWrapper);

        if (account != null){
            QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
            cartQueryWrapper.eq("itemid",itemId);
            Cart cart1 = cartMapper.selectOne(cartQueryWrapper);
            if (cart1 == null){
                Cart cart = new Cart();
                cart.setUserId(username);
                cart.setItemId(itemId);
                cart.setItemNum(1);
                cartMapper.insert(cart);
                return CommonResponse.createForSuccess("增添成功");
            }
            else{
                return updateCart(username, itemId, cart1.getItemNum()+1);
            }
        } else{
            return CommonResponse.createForError("添加失败 用户名不存在");
        }
    }

    @Override
    public CommonResponse updateCart(String username, String itemId,int num) {

        Cart cart = new Cart();
        cart.setUserId(username);
        cart.setItemId(itemId);
        cart.setItemNum(num);
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", username);
        updateWrapper.eq("itemid", itemId);
        int result = cartMapper.update(cart, updateWrapper);
        if(result != 0){
            return CommonResponse.createForSuccess("购物车更新成功");
        }else{
            return CommonResponse.createForError("购物车更新失败");
        }
//        Item item = itemMapper.selectById(itemId);
//
//        String productId = item.getProductId();
//        Product product = productMapper.selectById(productId);
//
//        ItemVO itemVO = itemToItemVO(item,product);

//        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userid",username);
//        List<Cart> cartList = cartMapper.selectList(queryWrapper);


//        List<CartVO> cartVOList = cartToCartVO(username);
//
//        int flag = 0;
//        for (int i = 0;i < cartVOList.size();i++){
//            CartVO cartVO = cartVOList.get(i);
//
//            if (cartVO.getItem().getItemId() == itemId){
//                cartVO.setQuantity(num);
//                cartVO.setInStock(true);
//
//                int number = cartVO.getQuantity();
//                double listPrice = item.getListPrice().doubleValue();
//                double total = 0;
//                for (int j = 1;j <= number;j++ ){
//                    total = total + listPrice;
//                }
//                cartVO.setTotal(BigDecimal.valueOf(total));
//                flag = 1;
//                break;
//            }
//        }
//
//        if (flag == 0){
//            CartVO cartVO = new CartVO();
//            cartVO.setItem(itemVO);
//            cartVO.setQuantity(num);
//            cartVO.setInStock(true);
//            int number = cartVO.getQuantity();
//            double listPrice = item.getListPrice().doubleValue();
//            double total = 0;
//            for (int j = 1;j <= number;j++ ){
//                total = total + listPrice;
//            }
//            cartVO.setTotal(BigDecimal.valueOf(total));
//            cartVOList.add(cartVO);
//        }

//        return CommonResponse.createForSuccessMessage("购物车更新成功");
    }

    @Override
    public CommonResponse<List<CartVO>> getCartByAccount(String username) {

        /*Cart cart = cartMapper.selectById(username);

        if (cart == null){
            return CommonResponse.createForError("该Account的购物车为空");
        }*/

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",username);

        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        if (cartList.isEmpty()){
            return CommonResponse.createForError("该Account的购物车为空");
        }

        List<CartVO> cartVOList = cartToCartVO(username);

        return CommonResponse.createForSuccess(cartVOList);
    }

    private List<CartVO> cartToCartVO(String username){
        List<CartVO> cartVOList = new ArrayList<>();


        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",username);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        if (cartList.isEmpty()){
            return null;
        }else {
            for (int i = 0;i < cartList.size();i++){
                CartVO cartVO = new CartVO();
                Cart cart = cartList.get(i);

                String itemId = cart.getItemId();
                Item item = itemMapper.selectById(itemId);

                String productId = item.getProductId();
                Product product = productMapper.selectById(productId);

                ItemVO itemVO = itemToItemVO(item,product);
                cartVO.setItem(itemVO);

                cartVO.setQuantity(cart.getItemNum());

                if(cartVO.getQuantity() > 0){
                    cartVO.setInStock(true);
                }else {
                    cartVO.setInStock(false);
                }

                int num = cartVO.getQuantity();
                double listPrice = item.getListPrice().doubleValue();
                double total = 0;
                for (int j = 1;j <= num;j++ ){
                    total = total + listPrice;
                }
                cartVO.setTotal(BigDecimal.valueOf(total));

                cartVOList.add(cartVO);
            }


            return cartVOList;
        }
    }

    @Override
    public CommonResponse removeItemByItemId(String username, String itemId) {

        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("userid",username);
        cartQueryWrapper.eq("itemid",itemId);
        Cart cart = cartMapper.selectOne(cartQueryWrapper);

        if (cart == null){
            return CommonResponse.createForError("删除失败，购物车内没有该商品");
        }else {
            cartMapper.delete(cartQueryWrapper);
            return CommonResponse.createForSuccessMessage("删除成功");
        }

        /*QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("userid",username);
        Account account = accountMapper.selectOne(accountQueryWrapper);

        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("userid",username);
        List<Cart> cartList = cartMapper.selectList(cartQueryWrapper);

        Item item = itemMapper.selectById(itemId);

        String productId = item.getProductId();
        Product product = productMapper.selectById(productId);

        ItemVO itemVO = itemToItemVO(item,product);

        CartVO cartVO = new CartVO();
        cartVO.setItem(itemVO);
        cartVO.setQuantity(quantity);
        cartVO.setInStock(true);
        cartVO.setTotal(total);

        List<CartVO> cartVOList = cartToCartVO(username);

        cartVOList.remove(itemVO);

        return CommonResponse.createForSuccessMessage("删除成功");*/
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
        /*itemVO.setCategoryId(product.getCategoryId());
        itemVO.setName(product.getName());
        itemVO.setDescription(product.getDescription());*/

        Inventory itemInventory = inventoryMapper.selectById(item.getItemId());
        itemVO.setQuantity(itemInventory.getQuantity());

        return itemVO;
    }
}
