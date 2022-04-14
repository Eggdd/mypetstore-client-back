package org.csu.mypetstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstoreclient.service.CatalogService;
import org.csu.mypetstoreclient.vo.LineItemVO;
import org.csu.mypetstoreclient.vo.OrderVO;
import org.csu.mypetstoreclient.common.CommonResponse;
import org.csu.mypetstoreclient.entity.*;
import org.csu.mypetstoreclient.persistence.*;
import org.csu.mypetstoreclient.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    //自动注入Mapper
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrdersMapper orderMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CatalogService catalogService;

    @Override
    public CommonResponse<OrderVO> insertOrder(OrderVO orderVO) {

        //获得当前订单编号
        int orderId = getNowSequenceIdAndUpdate();
        Orders order = new Orders();
        order.setOrderId(orderId);
        //设置下单时间
        order.setOrderDate(orderVO.getOrderDate());
        //获取发货商家的地址和名字
        Supplier supplier = supplierMapper.selectById(1);
        order.setShipAddress1(supplier.getAddr1());
        order.setShipAddress2(supplier.getAddr2());
        order.setUsername(supplier.getName());

        order.setUsername(orderVO.getUsername());
        order.setShipCity(orderVO.getShipCity());
        order.setShipState(orderVO.getShipState());
        order.setShipZip(orderVO.getShipZip());
        order.setShipCountry(orderVO.getShipCountry());
        order.setBillAddress1(orderVO.getBillAddress1());
        order.setBillAddress2(orderVO.getBillAddress2());
        order.setBillCity(orderVO.getBillCity());
        order.setBillState(orderVO.getBillState());
        order.setBillZip(orderVO.getBillZip());
        order.setBillCountry(orderVO.getBillCountry());
        order.setCourier(orderVO.getCourier());
        order.setTotalPrice(orderVO.getTotalPrice());
        order.setBillToFirstName(orderVO.getBillToFirstName());
        order.setBillToLastName(orderVO.getShipToLastName());
        order.setShipToFirstName(orderVO.getShipToFirstName());
        order.setShipToLastName(orderVO.getShipToLastName());
        order.setCreditCard(orderVO.getCreditCard());
        order.setExpiryDate(orderVO.getExpiryDate());
        order.setCardType(orderVO.getCardType());
        order.setLocale(orderVO.getLocale());

        //完成订单的插入
        orderMapper.insert(order);

        //进行orderStatus表的插入
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setTimeStamp(orderVO.getOrderDate());
        orderStatus.setLineNum(1);
        orderStatus.setStatus("undelivered");
        orderStatusMapper.insert(orderStatus);

        //获取lineitem数据库信息
        List<LineItemVO> lineItemVO = orderVO.getLineItems();
        List<LineItem> lineItemsList = new ArrayList<>();
        LineItem lineItems = new LineItem();
        lineItems.setItemId(lineItemVO.get(0).getItemId());
        lineItems.setOrderId(lineItemVO.get(0).getOrderId());
        lineItems.setLineNumber(lineItemVO.get(0).getLineNumber());
        lineItems.setUnitPrice(lineItemVO.get(0).getUnitPrice());
        lineItems.setQuantity(lineItemVO.get(0).getQuantity());
        lineItemsList.add(lineItems);

        System.out.println("add lineItemVO :" + lineItemVO);
        System.out.println("lineItems size :" + lineItemVO.size());
        System.out.println("lineItems.get(0) :" + lineItemVO.get(0));
        System.out.println("lineItems.indexOf(0) :" + lineItemVO.indexOf(0));

        //进行lineItem表的插入
        for (LineItem lineItem : lineItemsList) {
            lineItem.setOrderId(orderId);
            lineItem.setLineNumber(1);
            lineItem.setQuantity(lineItemsList.get(0).getQuantity());
            lineItem.setItemId(lineItemsList.get(0).getItemId());
            //插入unitPrice
            Item item = itemMapper.selectById(lineItem.getItemId());
            lineItem.setUnitPrice(item.getUnitCost());
            lineItemMapper.insert(lineItem);
        }
        if (orderMapper.selectById(orderVO.getUsername()) != null) {
            return CommonResponse.createForError("添加失败");
        }
        return CommonResponse.createForSuccessMessage("添加成功！");

    }

    @Override
    public CommonResponse<OrderVO> getOrderById(int Id) {
        Orders orders = orderMapper.selectById(Id);
        System.out.println("orders" + orders);

        if (orders == null) {
            return CommonResponse.createForError("获取失败");
        }

        QueryWrapper<LineItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderid", Id);
        List<LineItem> lineItemList = lineItemMapper.selectList(queryWrapper);
        System.out.println("lineitem" + lineItemList);

        List<LineItemVO> lineItemVOList = new ArrayList<>();

        for (int i=0; i<lineItemList.size(); i++){
            lineItemVOList.add(lineItemToLineItemVO(lineItemList.get(i)));
        }

        OrderStatus orderStatus = orderStatusMapper.selectById(Id);

        OrderVO OrderVO = entityToVO(orders, lineItemVOList, orderStatus);
        return CommonResponse.createForSuccess(OrderVO);
    }

    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(int orderId) {
        Orders orders = orderMapper.selectById(orderId);
        if (orders == null) {
            return CommonResponse.createForError("获取失败");
        }

        return getOrderById(orderId);
    }

    @Override
    public CommonResponse<List<OrderVO>> getOrdersByUsername(String username) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);

        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = orderMapper.selectList(queryWrapper);
        if (ordersList == null) {
            return CommonResponse.createForError("该用户没有订单");
        }
        for (int i=0; i<ordersList.size(); i++){
            orderVOList.add(getOrderByOrderId(ordersList.get(i).getOrderId()).getData());
        }
        return  CommonResponse.createForSuccess(orderVOList);



    }

    //获得当前订单编号
    public int getNowSequenceIdAndUpdate() {
        Sequence sequence = sequenceMapper.selectById("ordernum");
        int result = sequence.getNextId();
        sequence.setNextId(result + 1);
        //更新sequence中的nextId
        sequenceMapper.updateById(sequence);
        return result;
    }

    private OrderVO entityToVO(Orders order, List<LineItemVO> lineItemVOList, OrderStatus orderStatus) {
        OrderVO OrderVO = new OrderVO();
        OrderVO.setOrderId(order.getOrderId());
        OrderVO.setUsername(order.getUsername());
        OrderVO.setOrderDate(order.getOrderDate());
        OrderVO.setShipAddress1(order.getShipAddress1());
        OrderVO.setShipAddress2(order.getShipAddress2());
        OrderVO.setShipCity(order.getShipCity());
        OrderVO.setShipState(order.getShipState());
        OrderVO.setShipZip(order.getShipZip());
        OrderVO.setShipCountry(order.getShipCountry());
        OrderVO.setBillAddress1(order.getBillAddress1());
        OrderVO.setBillAddress2(order.getBillAddress2());
        OrderVO.setBillCity(order.getBillCity());
        OrderVO.setBillState(order.getBillState());
        OrderVO.setBillZip(order.getBillZip());
        OrderVO.setBillCountry(order.getBillCountry());
        OrderVO.setCourier(order.getCourier());
        OrderVO.setTotalPrice(order.getTotalPrice());
        OrderVO.setBillToFirstName(order.getBillToFirstName());
        OrderVO.setBillToLastName(order.getBillToLastName());
        OrderVO.setShipToFirstName(order.getShipToFirstName());
        OrderVO.setShipToLastName(order.getShipToLastName());
        OrderVO.setCreditCard(order.getCreditCard());
        OrderVO.setLocale(order.getLocale());
        OrderVO.setExpiryDate(order.getExpiryDate());
        OrderVO.setCardType(order.getCardType());

        OrderVO.setLineItems(lineItemVOList);
        OrderVO.setStatus(orderStatus.getStatus());
        return  OrderVO;
    }

    private LineItemVO lineItemToLineItemVO(LineItem lineItem){
        LineItemVO lineItemVO = new LineItemVO();
        lineItemVO.setItemId(lineItem.getItemId());
        lineItemVO.setLineNumber(lineItem.getLineNumber());
        lineItemVO.setQuantity(lineItem.getQuantity());
        lineItemVO.setUnitPrice(lineItem.getUnitPrice());

        BigDecimal num = new BigDecimal(lineItem.getQuantity());
        lineItemVO.setTotal(num.multiply(lineItem.getUnitPrice()));

        lineItemVO.setOrderId(lineItem.getOrderId());
        lineItemVO.setItem(catalogService.getItem(lineItem.getItemId()).getData());
        return lineItemVO;
    }
}
