package com.ra.model.dao.cart;

import com.ra.model.dao.product.IProductDAO;
import com.ra.model.dao.user.IUsersDAO;
import com.ra.model.entity.Cart;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Repository
public class CartDAO_Impl implements ICartDAO{
    @Autowired
    IUsersDAO usersDAO;
    @Override
    public List<Cart> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<Cart> carts = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_cart()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setCartId(resultSet.getInt("id"));
                cart.setUser(usersDAO.findById(resultSet.getInt("user_id")));

                carts.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return carts;
    }

    @Override
    public int saveCart(Cart cart) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL add_cart(?, ?)");
            callableStatement.setInt(1, cart.getUser().getUserId());
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.executeUpdate();
            return callableStatement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }

    }

    @Override
    public int updateCart(Cart cart) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL update_cart(?, ?)");
            callableStatement.setInt(1, cart.getUser().getUserId());
            callableStatement.setInt(2, cart.getCartId());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return 1;
    }

    @Override
    public void deleteCart(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL delete_cart(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
    }

    @Override
    public Cart findById(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        Cart cart = new Cart();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL find_cart_by_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cart.setCartId(resultSet.getInt("id"));
                cart.setUser(usersDAO.findById(resultSet.getInt("user_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cart;
    }

    @Override
    public Cart findCartByUserId(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        Cart cart = new Cart();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL find_cart_by_user_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cart.setCartId(resultSet.getInt("id"));
                cart.setUser(usersDAO.findById(resultSet.getInt("user_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cart;
    }
}
