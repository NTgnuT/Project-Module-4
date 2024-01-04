package com.ra.controller.admin;

import com.ra.model.entity.User;
import com.ra.model.service.user.IUserService;
import jdk.internal.classfile.impl.BufferedCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @RequestMapping("")
    public String index (Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user/index-user";
    }

    @GetMapping("/change-status-user/{id}")
    public String changeStatus (@PathVariable("id") Integer id) {
        userService.changeStatus(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/change-role-user/{id}")
    public String changeRole (@PathVariable("id") Integer id) {
        userService.changeRole(id);
        return "redirect:/admin/user";
    }
}
