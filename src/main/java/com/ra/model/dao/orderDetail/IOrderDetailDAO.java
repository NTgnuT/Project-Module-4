package com.ra.model.dao.orderDetail;

import com.ra.model.entity.CartItem;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailDAO {
    List<OrderDetail> findAll ();
    int addOrderDetail (List<CartItem> cartItems, Integer orderId);
    List<OrderDetail> findOrderDetailByOrderId (Integer orderId);
}
