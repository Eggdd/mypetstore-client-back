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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SequenceMapper sequenceMapper;

//    @PostMapping("")
//    @ResponseBody
//    public CommonResponse<OrderVO> insertOrder(
//            @RequestParam String username,
//            @RequestParam String orderDate,
//            @RequestParam String shipAddress1,
//            @RequestParam String shipAddress2,
//            @RequestParam String shipCity,
//            @RequestParam String shipState,
//            @RequestParam String shipZip,
//            @RequestParam String shipCountry,
//            @RequestParam String billAddress1,
//            @RequestParam String billAddress2,
//            @RequestParam String billCity,
//            @RequestParam String billState,
//            @RequestParam String billZip,
//            @RequestParam String billCountry,
//            @RequestParam String courier,
//            @RequestParam String totalPrice,
//            @RequestParam String billToFirstName,
//            @RequestParam String billToLastName,
//            @RequestParam String shipToFirstName,
//            @RequestParam String shipToLastName,
//            @RequestParam String creditCard,
//            @RequestParam String expiryDate,
//            @RequestParam String cardType,
//            @RequestParam String locale,
//            @RequestParam (value = "lineItem",required = false)List<String> lineItem,
//            HttpSession session) throws ParseException {
//
//        OrderVO OrderVO = new OrderVO();
//        Sequence sequence = sequenceMapper.selectById("ordernum");
//        int orderId = sequence.getNextId();
//        sequence.setNextId(orderId+1);
//        //更新sequence中的nextId
//        sequenceMapper.updateById(sequence);
//
//        OrderVO.setOrderId(orderId);
//        OrderVO.setUsername(username);
//
////        //转换
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        String time = sdf.format(orderDate);
////        Date orderdate = sdf.parse(time);
////        OrderVO.setOrderDate(orderdate);
//        System.out.println(orderDate);
//        //设置下单时间
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        OrderVO.setOrderDate(timestamp);
//        OrderVO.setShipAddress1(shipAddress1);
//        OrderVO.setShipAddress2(shipAddress2);
//        OrderVO.setShipCity(shipCity);
//        OrderVO.setShipState(shipState);
//        OrderVO.setShipZip(shipZip);
//        OrderVO.setShipCountry(shipCountry);
//        OrderVO.setBillAddress1(billAddress1);
//        OrderVO.setBillAddress2(billAddress2);
//        OrderVO.setBillCity(billCity);
//        OrderVO.setBillState(billState);
//        OrderVO.setBillZip(billZip);
//        OrderVO.setBillCountry(billCountry);
//        OrderVO.setCourier(courier);
//
//        BigDecimal totalprice = new BigDecimal(totalPrice);
//        OrderVO.setTotalPrice(totalprice);
//
//        OrderVO.setBillToFirstName(billToFirstName);
//        OrderVO.setBillToLastName(billToLastName);
//        OrderVO.setShipToFirstName(shipToFirstName);
//        OrderVO.setShipToLastName(shipToLastName);
//        OrderVO.setCreditCard(creditCard);
//        OrderVO.setExpiryDate(expiryDate);
//        OrderVO.setCardType(cardType);
//        OrderVO.setLocale(locale);
//
//        List<LineItem> lineItems = OrderVO.getLineItems();
//        lineItems.add((LineItem) lineItem);
//        OrderVO.setLineItems(lineItems);
//
//        CommonResponse<OrderVO> response = orderService.insertOrder(OrderVO);
//        if(response.isSuccess()){
//            session.setAttribute("insertOrder",response.getData());
//        }
//        return response;
//
//    }

    //增加订单
    @PostMapping("")
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
    @GetMapping("{id}")
    @ResponseBody
    public CommonResponse<OrderVO> getOrderByOrderId(@PathVariable("id") int id,HttpSession session) {
        CommonResponse<OrderVO> response = orderService.getOrderByOrderId(id);
        if(response.isSuccess()){
            session.setAttribute("getOrderByOrderId",response.getData());
        }
        return response;
    }

}
