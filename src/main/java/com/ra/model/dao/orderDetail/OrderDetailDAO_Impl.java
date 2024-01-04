package com.ra.model.dao.orderDetail;

import com.ra.model.dao.order.IOrderDAO;
import com.ra.model.dao.product.IProductDAO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.OrderDetail;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Repository
public class OrderDetailDAO_Impl implements IOrderDetailDAO{
    @Autowired
    IOrderDAO orderDAO;
    @Autowired
    IProductDAO productDAO;

    @Override
    public List<OrderDetail> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_order_detail()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderDAO.findByOrderId(resultSet.getInt("order_id")));
                orderDetail.setProduct(productDAO.findById(resultSet.getInt("product_id")));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getDouble("price"));

                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return orderDetails;
    }

    @Override
    public int addOrderDetail(List<CartItem> cartItems, Integer orderId) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL add_order_detail(?, ?, ?, ?)");
            for (CartItem cartItem : cartItems) {
                callableStatement.setInt(1, orderId);
                callableStatement.setInt(2, cartItem.getProduct().getProductId());
                callableStatement.setInt(3, cartItem.getQuantity());
                callableStatement.setDouble(4, cartItem.getProduct().getPrice());

                callableStatement.addBatch(); // Thêm dòng này để thêm callableStatement vào batch
            }
            callableStatement.executeBatch(); // Thực thi batch

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return 1;
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Integer orderId) {
        Connection connection = ConnectionDB.openConnection();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try {
            CallableStatement callableStatement= connection.prepareCall("CALL find_order_detail_by_order_id(?)");
            callableStatement.setInt(1, orderId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderDAO.findByOrderId(resultSet.getInt("order_id")));
                orderDetail.setProduct(productDAO.findById(resultSet.getInt("product_id")));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getDouble("price"));

                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return orderDetailList;
    }
}
