package com.ra.controller.user;

import com.ra.model.entity.User;
import com.ra.model.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
//@RequestMapping("/user")
public class ProfileController {
    @Value("${path}")
    private String path;
    @Autowired
    HttpSession session;
    @Autowired
    IUserService userService;

    @RequestMapping("/profile")
    private String profile(Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        User user = userService.findById(userLogin.getUserId());
        model.addAttribute("user", user);
        return "client/profile-user";
    }

    @PostMapping("/change-profile")
    public String changeProfile(@Valid @ModelAttribute("user") User newUser, RedirectAttributes attributes,
                                @RequestParam("avatar1") MultipartFile file,
                                @RequestParam("password") String newPass) {
        User userLogin = (User) session.getAttribute("userLogin");

        if (!userService.checkPassword(newPass, userLogin)) {
            attributes.addFlashAttribute("err_change_profile", "Confirm password is not correct");
            return "redirect:/profile";
        }

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            newUser.setAvatar(userLogin.getAvatar());
        } else {
            String fileName = file.getOriginalFilename();
            File destination = new File(path + fileName);
            try {
                // Cách 1:
                file.transferTo(destination);
                newUser.setAvatar(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        session.setAttribute("userLogin", newUser);
        userService.saveOrUpdate(newUser);
        return "redirect:/";
    }


}
