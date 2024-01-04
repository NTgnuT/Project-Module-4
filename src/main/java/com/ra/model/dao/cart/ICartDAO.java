package com.ra.model.dao.cart;

import com.ra.model.entity.Cart;

import java.util.List;

public interface ICartDAO {
    List<Cart> findAll ();
    int saveCart (Cart cart);
    int updateCart (Cart cart);
    void deleteCart (Integer id);
    Cart findById (Integer id);
    Cart findCartByUserId (Integer id);

}
