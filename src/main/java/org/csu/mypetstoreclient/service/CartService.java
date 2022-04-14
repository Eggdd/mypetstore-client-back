package org.csu.mypetstoreclient.service;

import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.vo.CartVO;

import java.util.List;

public interface CartService {

    CommonResponse addCart(String username,String itemId);

    CommonResponse updateCart(String username,String itemId,int num);

    CommonResponse<List<CartVO>> getCartByAccount(String username);

    CommonResponse removeItemByItemId(String username, String itemId);
}
