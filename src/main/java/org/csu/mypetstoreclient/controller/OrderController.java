package org.csu.mypetstoreclient.controller;

import org.csu.mypetstoreclient.vo.LineItemVO;
import org.csu.mypetstoreclient.vo.OrderVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.Sequence;
import org.csu.mypetstoreclient.persistence.SequenceMapper;
import org.csu.mypetstoreclient.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SequenceMapper sequenceMapper;

    //增加订单
    @PostMapping("/orders")
    @ResponseBody
    public CommonResponse<OrderVO> insertOrder(@RequestBody Map<String, Object> map, HttpSession session) throws ParseException {

        if (map.containsKey("username")
                && map.containsKey("orderDate")
                && map.containsKey("shipAddress1")
                && map.containsKey("shipAddress2")
                && map.containsKey("shipCity")
                && map.containsKey("shipState")
                && map.containsKey("shipZip")
                && map.containsKey("shipCountry")
                && map.containsKey("billAddress1")
                && map.containsKey("billAddress2")
                && map.containsKey("billCity")
                && map.containsKey("billState")
                && map.containsKey("billZip")
                && map.containsKey("billCountry")
                && map.containsKey("courier")
                && map.containsKey("totalPrice")
                && map.containsKey("billToFirstName")
                && map.containsKey("billToLastName")
                && map.containsKey("shipToFirstName")
                && map.containsKey("shipToLastName")
                && map.containsKey("creditCard")
                && map.containsKey("expiryDate")
                && map.containsKey("cardType")
                && map.containsKey("locale")) {
            //判断传入数据合法

            //获取到lineItems
            ArrayList<Object> list = (ArrayList<Object>) map.get("lineItems");
            ArrayList<LineItemVO> lineItems = new ArrayList<>();
            LineItemVO lineItem = null;
            for (Object object : list) {
                lineItem = new LineItemVO();
                lineItem.setItemId(((LinkedHashMap<Object, Object>) object).get("itemId").toString());
                lineItem.setQuantity(Integer.parseInt(((LinkedHashMap<Object, Object>) object).get("quantity").toString()));
                lineItems.add(lineItem);
            }
            System.out.println(lineItems);

            //获取到OrderVO信息
            OrderVO OrderVO = new OrderVO();
            Sequence sequence = sequenceMapper.selectById("ordernum");
            int orderId = sequence.getNextId();
            sequence.setNextId(orderId + 1);
            //更新sequence中的nextId
            sequenceMapper.updateById(sequence);

            OrderVO.setOrderId(orderId);
            OrderVO.setUsername(map.get("username").toString());
            System.out.println(map.get("orderDate").toString());

            //设置下单时间

            Date orderdate =new SimpleDateFormat("yyyy-MM-dd").parse((map.get("orderDate")).toString());
            OrderVO.setOrderDate(orderdate);

            OrderVO.setShipAddress1(map.get("shipAddress1").toString());
            OrderVO.setShipAddress2(map.get("shipAddress2").toString());
            OrderVO.setShipCity(map.get("shipCity").toString());
            OrderVO.setShipState(map.get("shipState").toString());
            OrderVO.setShipZip(map.get("shipZip").toString());
            OrderVO.setShipCountry(map.get("shipCountry").toString());
            OrderVO.setBillAddress1(map.get("billAddress1").toString());
            OrderVO.setBillAddress2(map.get("billAddress2").toString());
            OrderVO.setBillCity(map.get("billCity").toString());
            OrderVO.setBillState(map.get("billState").toString());
            OrderVO.setBillZip(map.get("billZip").toString());
            OrderVO.setBillCountry(map.get("billCountry").toString());
            OrderVO.setCourier(map.get("courier").toString());

            BigDecimal totalprice = new BigDecimal(map.get("totalPrice").toString());
            OrderVO.setTotalPrice(totalprice);

            OrderVO.setBillToFirstName(map.get("billToFirstName").toString());
            OrderVO.setBillToLastName(map.get("billToLastName").toString());
            OrderVO.setShipToFirstName(map.get("shipToFirstName").toString());
            OrderVO.setShipToLastName(map.get("shipToLastName").toString());
            OrderVO.setCreditCard(map.get("creditCard").toString());
            OrderVO.setExpiryDate(map.get("expiryDate").toString());
            OrderVO.setCardType(map.get("cardType").toString());
            OrderVO.setLocale(map.get("locale").toString());
            OrderVO.setLineItems(lineItems);

            CommonResponse<OrderVO> response = orderService.insertOrder(OrderVO);
            if (response.isSuccess()) {
                session.setAttribute("insertOrder", response.getData());
            }
            return response;
        } else return CommonResponse.createForError("添加失败");
    }

    //根据订单id获得订单
    @GetMapping("/orders/{id}")
    @ResponseBody
    public CommonResponse<OrderVO> getOrderByOrderId(@PathVariable("id") int id,HttpSession session) {
        CommonResponse<OrderVO> response = orderService.getOrderByOrderId(id);
        if(response.isSuccess()){
            session.setAttribute("getOrderByOrderId",response.getData());
        }
        return response;
    }

    @GetMapping("/accounts/{username}/orders")
    @ResponseBody
    public CommonResponse<List<OrderVO>> getOrderByUserName(@PathVariable("username")String username){
        return orderService.getOrdersByUsername(username);
    }

}
