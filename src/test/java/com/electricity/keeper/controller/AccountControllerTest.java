package com.electricity.keeper.controller;

import com.electricity.keeper.repository.AccountRepository;
import com.electricity.keeper.repository.HistoryRepository;
import com.electricity.keeper.repository.UserRepository;
import com.electricity.keeper.service.AccountServiceImpl;
import com.electricity.keeper.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    HistoryRepository historyRepository;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Test
    void getAccounts() {

    }

    @Test
    void addAccount() {
    }

    @Test
    void getAccount() {
    }

    @Test
    void changeAccountNumber() {
    }

    @Test
    void addNewHistoryToAccount() {
    }

    @Test
    void deleteAllAccountHistory() {
    }
}