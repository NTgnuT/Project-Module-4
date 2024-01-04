package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
public class LoginController {
    @Value("${path}")
    private String path;
    @Autowired
    UserServiceIMPL userServiceIMPL;
    @Autowired
    private HttpSession session;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user") User user,BindingResult result,RedirectAttributes redirectAttributes,
                             @RequestParam("avatar1") MultipartFile file
                             ) {
        if (result.hasErrors()) {
            return "/register";
        } else {
            if (userServiceIMPL.checkEmail(user.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("err_register", "Email đã tồn tại !!!");
                return "redirect:/register";
            } else {
                String fileName = file.getOriginalFilename();
                File destination = new File(path + fileName);
                try {
                    // Cách 1:
                    file.transferTo(destination);
                    user.setAvatar(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (userServiceIMPL.saveOrUpdate(user)) {
                    redirectAttributes.addFlashAttribute("success", "Đăng ký tài khoản thành công");
                    return "redirect:/login";
                }
            }
        }
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("userLogin", user);
        return "/login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("userLogin") User user, RedirectAttributes redirectAttributes) {
        User userLogin = userServiceIMPL.login(user.getEmail(), user.getPassword());

        if (userLogin != null ) {
            if (!userLogin.isStatus()) {
                redirectAttributes.addFlashAttribute("err_login", "Your account has been blocked !!!");
                return "redirect:/login";
            }
            if (userLogin.isRole()) {
                session.setAttribute("userLogin", userLogin);
                return "redirect:/";
            } else {
                session.setAttribute("adminLogin", userLogin    );
                return "redirect:/admin";
            }
        }

        redirectAttributes.addFlashAttribute("err", "Email hoặc mật khẩu của bạn không khớp!!!");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("userLogin");
        session.removeAttribute("adminLogin");
        return "redirect:/";
    }
}
