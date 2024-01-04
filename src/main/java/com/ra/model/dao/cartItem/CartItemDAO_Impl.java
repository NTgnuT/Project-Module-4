package com.ra.model.dao.cartItem;

import com.ra.model.dao.cart.ICartDAO;
import com.ra.model.dao.product.IProductDAO;
import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartItemDAO_Impl implements ICartItemDAO{
    @Autowired
    IProductDAO productDAO;
    @Autowired
    ICartDAO cartDAO;
    @Override
    public List<CartItem> getCartItem() {
        Connection collection = ConnectionDB.openConnection();
        List<CartItem> cartItems = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_cart_item()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setProduct(productDAO.findById(resultSet.getInt("pro_id")));
                cartItem.setCart(cartDAO.findById(resultSet.getInt("cart_id")));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cartItems;
    }

    @Override
    public void delete(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL delete_cart_item(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
    }

    @Override
    public int addCartItem(CartItem cartItem) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL add_to_cart_item(?, ?, ?)");
            callableStatement.setInt(1, cartItem.getProduct().getProductId());
            callableStatement.setInt(2, cartItem.getCart().getCartId());
            callableStatement.setInt(3, cartItem.getQuantity());

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return 1;
    }

    @Override
    public int updateCartItem(CartItem cartItem) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL update_cart_item(?, ?, ?, ?)");
            callableStatement.setInt(1, cartItem.getProduct().getProductId());
            callableStatement.setInt(2, cartItem.getCart().getCartId());
            callableStatement.setInt(3, cartItem.getQuantity());
            callableStatement.setInt(4, cartItem.getId());

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return 1;
    }


    @Override
    public CartItem findById(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        CartItem cartItem = new CartItem();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL find_cart_item_by_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setProduct(productDAO.findById(resultSet.getInt("pro_id")));
                cartItem.setCart(cartDAO.findById(resultSet.getInt("cart_id")));
                cartItem.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cartItem;
    }

    @Override
    public List<CartItem> findCartItemByCartId(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        List<CartItem> cartItems = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL find_cart_item_by_cart_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setProduct(productDAO.findById(resultSet.getInt("pro_id")));
                cartItem.setCart(cartDAO.findById(resultSet.getInt("cart_id")));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cartItems;
    }

    @Override
    public CartItem findByProId(Integer id) {
        Connection collection = ConnectionDB.openConnection();
        CartItem cartItem = new CartItem();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL find_cart_item_by_pro_id(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setProduct(productDAO.findById(resultSet.getInt("pro_id")));
                cartItem.setCart(cartDAO.findById(resultSet.getInt("cart_id")));
                cartItem.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return cartItem;
    }

    @Override
    public void deleteAll(Integer cartId) {
        Connection collection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL delete_cart_item_by_cart_id(?)");
            callableStatement.setInt(1, cartId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
    }
}
