package org.csu.mypetstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstoreclient.vo.ItemDetailsVO;
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

        LineItem lineItem = lineItemMapper.selectById(Id);
        System.out.println("lineitem" + lineItem);

        Item item = itemMapper.selectById(lineItem.getItemId());
        System.out.println("item" + item);

        Product product = productMapper.selectById(item.getProductId());
        System.out.println("product" + product);

        if (orders == null) {
            return CommonResponse.createForError("获取失败");
        }

        OrderVO OrderVO = entityToVO(orders, lineItem, item, product);
        return CommonResponse.createForSuccess(OrderVO);
    }

    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(int orderId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderid", orderId);

        Orders orders = orderMapper.selectOne(queryWrapper);
        if (orders == null) {
            return CommonResponse.createForError("获取失败");
        }

        return getOrderById(orderId);
    }

    @Override
    public CommonResponse<OrderVO> getOrdersByUsername(String username) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);

        Orders orders = orderMapper.selectOne(queryWrapper);
        if (orders == null) {
            return CommonResponse.createForError("获取失败");
        }

        return getOrderById(orders.getOrderId());
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

    private OrderVO entityToVO(Orders order, LineItem lineItem, Item item, Product product) {
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
        List<LineItemVO> lineItemVOS = new ArrayList<>();

        while (lineItemMapper.selectById(order.getOrderId()) != null) {
            LineItemVO lineItemVO = new LineItemVO();
            lineItemVO.setOrderId(lineItem.getOrderId());
            lineItemVO.setLineNumber(lineItem.getLineNumber());
            lineItemVO.setItemId(lineItem.getItemId());
            lineItemVO.setQuantity(lineItem.getQuantity());
            lineItemVO.setUnitPrice(lineItem.getUnitPrice());

            int quantity = lineItem.getQuantity();
            int unitPrice = lineItem.getUnitPrice().intValue();
            lineItemVO.setTotal(BigDecimal.valueOf(quantity * unitPrice));

            ItemDetailsVO itemDetailsVO = new ItemDetailsVO();
            itemDetailsVO.setItemId(item.getItemId());
            itemDetailsVO.setProductId(item.getProductId());
            itemDetailsVO.setListPrice(item.getListPrice());
            itemDetailsVO.setUnitCost(item.getUnitCost());
            itemDetailsVO.setSupplierId(item.getSupplierId());
            itemDetailsVO.setStatus(item.getStatus());
            itemDetailsVO.setAttribute1(item.getAttribute1());
            itemDetailsVO.setAttribute2(item.getAttribute2());
            itemDetailsVO.setAttribute3(item.getAttribute3());
            itemDetailsVO.setAttribute4(item.getAttribute4());
            itemDetailsVO.setAttribute5(item.getAttribute5());
            itemDetailsVO.setQuantity(10000);
            itemDetailsVO.setProduct(product);
            lineItemVO.setItem(itemDetailsVO);
            lineItemVOS.add(lineItemVO);

        }

        OrderVO.setLineItems(lineItemVOS);
        return OrderVO;
    }
}
