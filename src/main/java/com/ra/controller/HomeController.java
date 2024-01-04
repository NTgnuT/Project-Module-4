package com.ra.controller;

import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;
    @Autowired
    IProductService productService;
@RequestMapping("/")
    public String home (Model model) {
    List<Product> productList = productService.findAll();
    User userFromSession = (User) session.getAttribute("user");
    model.addAttribute("user", userFromSession);
    model.addAttribute("productList", productList);
    return "home";
}
@RequestMapping("/about")
    public String about() {
    return "client/about";
}
@RequestMapping("/blog")
    public String blog () {
    return "client/blog";
}
@RequestMapping("/contact")
    public String contact () {
    return "client/contact";
}
}

