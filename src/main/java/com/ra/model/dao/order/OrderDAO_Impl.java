package com.ra.model.dao.order;

import com.ra.model.dao.user.IUsersDAO;
import com.ra.model.entity.Order;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Repository
public class OrderDAO_Impl implements IOrderDAO{
    @Autowired
    IUsersDAO usersDAO;
    @Override
    public List<Order> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<Order> orders = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_orders()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setUser(usersDAO.findById(resultSet.getInt("user_id")));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getInt("status"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return orders;
    }

    @Override
    public int addOrder(Order order) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL add_orders(?, ?, ?, ?, ?)");
            callableStatement.setInt(1, order.getUser().getUserId());
            callableStatement.setDouble(2, order.getTotal());
            callableStatement.setString(3, order.getAddress());
            callableStatement.setString(4, order.getNote());
            callableStatement.registerOutParameter(5, Types.INTEGER);

            callableStatement.executeUpdate();
            return callableStatement.getInt(5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void changeStatus(Integer status, Integer orderId) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL change_status_orders(?, ?)");
            callableStatement.setInt(1, status);
            callableStatement.setInt(2, orderId);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public Order findByOrderId(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        Order order = new Order();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_order_by_order_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                order.setOrderId(resultSet.getInt("id"));
                order.setUser(usersDAO.findById(resultSet.getInt("user_id")));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getInt("status"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return order;
    }

    @Override
    public Order findByUserId(Integer userId) {
        Connection connection = ConnectionDB.openConnection();
        Order order = new Order();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_order_by_user_id(?)");
            callableStatement.setInt(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                order.setOrderId(resultSet.getInt("id"));
                order.setUser(usersDAO.findById(resultSet.getInt("user_id")));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getInt("status"));
                order.setOrder_at(resultSet.getDate("order_at"));
                order.setAddress(resultSet.getString("address"));
                order.setNote(resultSet.getString("note"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return order;
    }
}
