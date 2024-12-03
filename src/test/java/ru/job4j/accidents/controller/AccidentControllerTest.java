package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void whenCreateAccidentMethodThenShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithMockUser
    void whenEditAccidentMethodThenShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/editAccident/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attributeExists("accident"));
    }
}