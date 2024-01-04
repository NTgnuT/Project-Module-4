package com.ra.model.service.cart;

import com.ra.model.entity.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> findAll ();
    int saveCart (Cart cart);
    int updateCart (Cart cart);
    void deleteCart (Integer id);
    Cart findById (Integer id);
    Cart findCartByUserId (Integer id);
    int getCartId (Integer userId);
}
