package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        User user = new User();
        user.setUsername("Guest");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            if (auth.getPrincipal() instanceof User) {
                user = (User) auth.getPrincipal();
            } else {
                LOG.info("Пользователь не авторизовался");
            }
        }
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("user", user);
        model.addAttribute("errorMessage", errorMessage);
        return "/user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/user/login?logout=true";
    }
}
