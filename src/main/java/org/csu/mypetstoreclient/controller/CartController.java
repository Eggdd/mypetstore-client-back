package org.csu.mypetstoreclient.controller;

import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.service.CartService;
import org.csu.mypetstoreclient.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    //1.  添加商品到购物车（每次添加一个）
    @PostMapping("accounts/{username}/carts/{itemId}")
    @ResponseBody
    public CommonResponse addCart(
            @PathVariable("username") String username,
            @PathVariable("itemId") String itemId,
            HttpSession session){
        return cartService.addCart(username,itemId);
    }

    //2.  更新购物车
    @PutMapping("accounts/{username}/carts")
    @ResponseBody
    public CommonResponse updateCart(
            @PathVariable("username") String username,
            @RequestParam("itemId") String itemId,
            @Valid @RequestParam("quantity") int num){
        return cartService.updateCart(username,itemId,num);
    }

    //3.  获得某个账号下的所有购物车
    @GetMapping("accounts/{username}/carts")
    @ResponseBody
    public CommonResponse<List<CartVO>> getCartByAccount(
            @PathVariable("username") String username){
        return cartService.getCartByAccount(username);
    }

    @DeleteMapping("accounts/{username}/carts/{itemId}")
    @ResponseBody
    public CommonResponse removeItemByItemId(
            @PathVariable("username") String username,
            @PathVariable("itemId") String itemId){
        return cartService.removeItemByItemId(username,itemId);
    }
}
