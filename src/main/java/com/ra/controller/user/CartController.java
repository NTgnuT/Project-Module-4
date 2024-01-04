package com.ra.controller.user;

import com.ra.model.entity.*;
import com.ra.model.service.cart.ICartService;
import com.ra.model.service.cartItem.ICartItemService;
import com.ra.model.service.order.IOrderService;
import com.ra.model.service.orderDetail.IOrderDetailService;
import com.ra.model.service.product.IProductService;
import jdk.internal.classfile.impl.BufferedCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
    ICartItemService cartItemService;
    @Autowired
    IProductService productService;
    @Autowired
    HttpSession session;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    @RequestMapping("/cart")
    public String cart (Model model) {
        User user = (User) session.getAttribute("userLogin");
        if (user != null ) {
            Cart cart = cartService.findCartByUserId(user.getUserId());
            List<CartItem> cartItems = cartItemService.findCartItemByCartId(cart.getCartId());
            model.addAttribute("cartItems", cartItems);

            double subTotal = getTotal(cartItems);
            model.addAttribute("subTotal", subTotal);
            return "client/cart";
        }
        return "redirect:/login";
    }

    @GetMapping("/up-qty/{id}")
    public String up_qty (@PathVariable("id") Integer id) {
        CartItem cartItem = cartItemService.findByProId(id);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemService.updateCartItem(cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/down-qty/{id}")
    public String down_qty (@PathVariable("id") Integer id) {
        CartItem cartItem = cartItemService.findByProId(id);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemService.updateCartItem(cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/delete-cart/{id}")
    public String delete_item (@PathVariable("id") Integer id) {
        cartItemService.delete(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear-cart")
    public String clear_cart () {
        User userLogin = (User) session.getAttribute("userLogin");
        Cart cart = cartService.findCartByUserId(userLogin.getUserId());

        cartItemService.deleteAll(cart.getCartId());
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout (Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        Cart cart = cartService.findCartByUserId(userLogin.getUserId());

        List<CartItem> cartItemList = cartItemService.findCartItemByCartId(cart.getCartId());
        double subTotal = getTotal(cartItemList);
        model.addAttribute("userLogin", userLogin);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("subTotal", subTotal);

        return "client/check-out";
    }

    @PostMapping("/checkout")
    public String addCheckout (@RequestParam("note") String note, @RequestParam("address") String address) {
        User userLogin = (User) session.getAttribute("userLogin");
        Cart cart = cartService.findCartByUserId(userLogin.getUserId());
        List<CartItem> cartItemList = cartItemService.findCartItemByCartId(cart.getCartId());
        double total = getTotal(cartItemList);

        Order order = new Order();
        order.setUser(userLogin);
        order.setTotal(total);
        order.setAddress(address);
        order.setNote(note);

        int orderIdNew = orderService.addOrder(order);

        orderDetailService.addOrderDetail(cartItemList, orderIdNew);

        cartItemService.deleteAll(cart.getCartId());

        return "client/thankyou";
    }



    public double getTotal (List<CartItem> cartItems) {
        double subTotal = 0;
        for (CartItem cartItem : cartItems) {
            subTotal += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        return subTotal;
    }





}
