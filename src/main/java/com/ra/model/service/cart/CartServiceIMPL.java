package com.ra.model.service.cart;

import com.ra.model.dao.cart.ICartDAO;
import com.ra.model.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceIMPL implements ICartService{
    @Autowired
    ICartDAO cartDAO;

    @Override
    public List<Cart> findAll() {
        return cartDAO.findAll();
    }

    @Override
    public int saveCart(Cart cart) {
        return cartDAO.saveCart(cart);
    }

    @Override
    public int updateCart(Cart cart) {
        return cartDAO.updateCart(cart);
    }

    @Override
    public void deleteCart(Integer id) {
        cartDAO.deleteCart(id);
    }

    @Override
    public Cart findById(Integer id) {
        return cartDAO.findById(id);
    }

    @Override
    public Cart findCartByUserId(Integer id) {
        return cartDAO.findCartByUserId(id);
    }

    @Override
    public int getCartId(Integer userId) {
        int cartId;
        Cart newCart = cartDAO.findCartByUserId(userId);
        if (newCart != null) {
            cartId = newCart.getCartId();
        } else {
            cartId = cartDAO.saveCart(newCart);
        }
        return cartId;
    }


}
