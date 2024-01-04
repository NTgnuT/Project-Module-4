package com.ra.model.service.cartItem;

import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;

import java.util.List;

public interface ICartItemService {
    List<CartItem> getCartItem();
    void delete(Integer id);
    int addCartItem(CartItem cartItem);
    int updateCartItem(CartItem cartItem);
    CartItem findById(Integer id);
    List<CartItem> findCartItemByCartId(Integer id);
    CartItem findByProId (Integer id);
    void deleteAll (Integer cartId);
}
