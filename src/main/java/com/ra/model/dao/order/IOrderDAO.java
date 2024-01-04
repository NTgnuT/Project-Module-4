package com.ra.model.dao.order;

import com.ra.model.entity.Order;

import java.util.List;

public interface IOrderDAO {
    List<Order> findAll ();
    int addOrder (Order order);
    void changeStatus (Integer status, Integer orderId);
    Order findByOrderId (Integer id);
    Order findByUserId (Integer userId);
}
