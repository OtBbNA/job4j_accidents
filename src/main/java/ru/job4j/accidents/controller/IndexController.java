package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentSpringDataService;
import ru.job4j.accidents.service.RuleService;
import ru.job4j.accidents.service.TypeService;

@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentSpringDataService accidentService;
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        user.setUsername("Guest");
        if (auth != null && auth.isAuthenticated()) {
            if (auth.getPrincipal() instanceof User) {
                user = (User) auth.getPrincipal();
            } else {
                LOG.info("Пользователь не авторизовался");
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}
