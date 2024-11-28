package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.RuleService;
import ru.job4j.accidents.service.TypeService;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidents;
    private final TypeService types;
    private final RuleService rules;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types.findAll());
        model.addAttribute("rules", rules.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.create(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/editAccident/{id}")
    public String viewEditAccident(@PathVariable int id, Model model) {
        var accident = accidents.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Что-то пошло не так, возможно инцидент уже обработан");
            return "error/404";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", types.findAll());
        model.addAttribute("rules", rules.findAll());
        return "accident/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.update(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable int id, Model model) {
        accidents.deleteById(id);
        return "redirect:/index";
    }
}
