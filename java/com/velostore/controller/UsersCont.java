package com.velostore.controller;

import com.velostore.controller.main.Attributes;
import com.velostore.model.User;
import com.velostore.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersCont extends Attributes {
    @GetMapping
    public String Users(Model model) {
        addAttributesUsers(model);
        return "users";
    }

    @PostMapping("/update/{id}")
    public String UserUpdate(@PathVariable Long id, @RequestParam Role role) {
        User user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String UserDelete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }


}
