package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident/{id}")
    public String viewEditAccident(@PathVariable int id, Model model) {
        var accident = accidents.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Что-то пошло не так, возможно инцидент уже обработан");
            return "error/404";
        }
        model.addAttribute("accident", accident);
        return "accident/editAccident";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident) {
        accidents.update(accident);
        return "redirect:/index";
    }
}
