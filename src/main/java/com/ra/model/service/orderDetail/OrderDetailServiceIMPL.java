package com.ra.model.service.orderDetail;

import com.ra.model.dao.orderDetail.IOrderDetailDAO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceIMPL implements IOrderDetailService{
    @Autowired
    IOrderDetailDAO orderDetailDAO;
    @Override
    public List<OrderDetail> findAll() {
        return orderDetailDAO.findAll();
    }

    @Override
    public int addOrderDetail(List<CartItem> cartItems, Integer orderId) {
        return orderDetailDAO.addOrderDetail(cartItems, orderId);
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Integer orderId) {
        return orderDetailDAO.findOrderDetailByOrderId(orderId);
    }
}
