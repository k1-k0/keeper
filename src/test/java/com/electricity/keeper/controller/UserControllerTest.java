package com.electricity.keeper.controller;

import com.electricity.keeper.model.User;
import com.electricity.keeper.repository.UserRepository;
import com.electricity.keeper.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    User tester;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void homeUserLogged() throws Exception {
        var username = "tester";

        Mockito.when(userService.getCurrentUsername()).thenReturn(username);
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hi," + username)));
    }

    @Test
    void loginSuccessfully() throws Exception {
        Mockito.when(userService.verifyUser("tester", "12345")).thenReturn(true);
        var request = post("/login").queryParam("username", "tester").queryParam("password", "12345");
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("login success")));
    }

    @Test
    void loginFail() throws Exception {
        Mockito.when(userService.verifyUser("tester", "123")).thenReturn(false);

        var request = post("/login")
                .queryParam("username", "tester")
                .queryParam("password", "123");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsStringIgnoringCase("Incorrect user credentials!")));
    }

    @Test
    void registerSuccess() throws Exception {
        var username = "tester";
        var password = "12345";
        var email = "mail@mail.com";
        var request = post("/register")
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("confirm", password)
                .queryParam("email", email);

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("register success")));
    }
}