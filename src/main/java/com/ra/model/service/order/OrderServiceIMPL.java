package com.ra.model.service.order;

import com.ra.model.dao.order.IOrderDAO;
import com.ra.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceIMPL implements IOrderService{
    @Autowired
    IOrderDAO orderDAO;
    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public int addOrder(Order order) {
        return orderDAO.addOrder(order);
    }

    @Override
    public void changeStatus(Integer status, Integer orderId) {
        orderDAO.changeStatus(status, orderId);
    }

    @Override
    public Order findByOrderId(Integer id) {
        return orderDAO.findByOrderId(id);
    }

    @Override
    public Order findByUserId(Integer userId) {
        return findByUserId(userId);
    }
}
