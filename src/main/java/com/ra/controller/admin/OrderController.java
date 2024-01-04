package com.ra.controller.admin;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.service.order.IOrderService;
import com.ra.model.service.orderDetail.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    @RequestMapping("")
    public String index (Model model) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orderList", orderList);
        return "admin/order/index-order";
    }

    @GetMapping("/accept-order/{id}")
    public String accept_order (@PathVariable("id") Integer id) {
        orderService.changeStatus(1, id);
        return "redirect:/admin/order";
    }

    @GetMapping("/cancel-order/{id}")
    public String cancel_order (@PathVariable("id") Integer id) {
        orderService.changeStatus(2, id);
        return "redirect:/admin/order";
    }

    @GetMapping("/order-detail/{id}")
    public String show_order_detail (@PathVariable("id") Integer orderId, Model model) {
        Order order = orderService.findByOrderId(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("orderDetailList", orderDetailList);

        return "/admin/order/order-detail";
    }


}
