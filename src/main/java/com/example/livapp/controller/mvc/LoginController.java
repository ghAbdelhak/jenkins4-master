package com.example.livapp.controller.mvc;


import com.example.livapp.model.user.User;
import com.example.livapp.service.abstraction.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
        }

        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté avec succès");
        }

        return "login";
    }







    @GetMapping("/logout")
    public String logout(Authentication authentication,HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,authentication);
        return "redirect:/login?logout=true";
    }


    @GetMapping("/showChangePassword")
    public String showChangePassword(@RequestParam(value = "messageCode", required = false) Integer messageCode ) {

        return "change_password";
    }



    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam Map<String,String> params) {
        String oldPassword = params.get("oldpassword");
        String password1 = params.get("password1");
        String password2= params.get("password2");
        User user = userService.getConnectedUser();
        String userPassword = user.getPassword();
        if(userService.isOldPasswordCorrect(userPassword,oldPassword)) {
            if(password1.equals(password2)) {
                if (!password1.equals(oldPassword)) {
                    userService.changePassword(user, password1);
                }
                else {
                    return "redirect:/showChangePassword?messageCode=0";
                }

            }

            else {
                return "redirect:/showChangePassword?messageCode=1";
            }
        }
        else {
            return "redirect:/showChangePassword?messageCode=2";
        }

        return "redirect:/showChangePassword?messageCode=3";
    }


}

