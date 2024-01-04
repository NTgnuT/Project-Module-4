package com.ra.model.service.cartItem;

import com.ra.model.dao.cartItem.ICartItemDAO;
import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartItemServiceIMPL implements ICartItemService{
    @Autowired
    ICartItemDAO cartItemDAO;
    @Override
    public List<CartItem> getCartItem() {
        return cartItemDAO.getCartItem();
    }

    @Override
    public void delete(Integer id) {
        cartItemDAO.delete(id);
    }

    @Override
    public int addCartItem(CartItem cartItem) {
        return cartItemDAO.addCartItem(cartItem);
    }

    @Override
    public int updateCartItem(CartItem cartItem) {
        return cartItemDAO.updateCartItem(cartItem);
    }

    @Override
    public CartItem findById(Integer id) {
        return cartItemDAO.findById(id);
    }

    @Override
    public List<CartItem> findCartItemByCartId(Integer id) {
        return cartItemDAO.findCartItemByCartId(id);
    }

    @Override
    public CartItem findByProId(Integer id) {
        return cartItemDAO.findByProId(id);
    }

    @Override
    public void deleteAll(Integer cartId) {
        cartItemDAO.deleteAll(cartId);
    }
}
