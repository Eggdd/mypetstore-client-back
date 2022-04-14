package org.csu.mypetstoreclient.service;

import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.vo.OrderVO;

public interface OrderService {

    CommonResponse<OrderVO> insertOrder(OrderVO orderVO);

    CommonResponse<OrderVO>  getOrderById(int Id);

    CommonResponse<OrderVO>  getOrderByOrderId(int orderId);

    CommonResponse<OrderVO> getOrdersByUsername(String username);

}
