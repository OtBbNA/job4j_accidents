package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.TypeService;

@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentService accidentService;
    private final TypeService typeService;

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("user", "User Name");
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("types", typeService.findAll());
        return "index";
    }
}
