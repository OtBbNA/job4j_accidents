package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentSpringDataService;

import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentSpringDataService accidentService;

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

    @Test
    @WithMockUser
    void whenSaveAccidentThenShouldRedirectToIndex() throws Exception {
        Accident accident = new Accident();
        mockMvc.perform(post("/saveAccident").flashAttr("accident", accident))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argumentCaptor.capture(), any());
        assertThat(argumentCaptor.getValue()).isEqualTo(accident);
    }

    @Test
    @WithMockUser
    void whenEditAccidentThenShouldRedirectToIndex() throws Exception {
        Accident accident = new Accident();
        mockMvc.perform(post("/editAccident")
                        .flashAttr("accident", accident))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argumentCaptor.capture(), any());
        assertThat(argumentCaptor.getValue()).isEqualTo(accident);
    }

    @Test
    @WithMockUser
    public void whenGetDeletePageThenRedirectToIndex() throws Exception {
        mockMvc.perform(get("/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }
}