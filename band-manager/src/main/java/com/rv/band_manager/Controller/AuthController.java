package com.rv.band_manager.Controller;

import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.rv.band_manager.Model.User;
import com.rv.band_manager.Service.*;

@Controller
public class AuthController {
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserServiceImpl userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String performRegister(@Valid @ModelAttribute User user,
                                  BindingResult result, Model model){
        if(result.hasErrors()){
            return "register";
        }
        try{
            userService.register(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        model.addAttribute("successMessage",
                "User created successfully, please login");
        return "/login";
    }

    @GetMapping("/my-account")
    public String dashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication == null){
            return "login";
        }
        model.addAttribute("username", authentication.getName());
        return "my-account";
    }
}
