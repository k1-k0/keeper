package com.electricity.keeper.service;

import com.electricity.keeper.model.Account;
import com.electricity.keeper.model.History;
import com.electricity.keeper.model.User;
import com.electricity.keeper.repository.AccountRepository;
import com.electricity.keeper.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.NoSuchElementException;

@SpringBootTest
class AccountServiceTest {

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var username = "tester";
        var user = new User();
        user.setUsername(username);

        var first = new Account(876543211);
        first.addHistory(new History(18320L));
        first.addHistory(new History(18444L));

        var second = new Account(122343211);
        second.addHistory(new History(7370L));
        second.addHistory(new History(8884L));

        user.addAccount(first);
        user.addAccount(second);

        Mockito.when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(first));
        Mockito.when(accountRepository.findById(2L)).thenReturn(java.util.Optional.of(second));
        Mockito.when(userService.getCurrentUsername()).thenReturn(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        Mockito.when(accountRepository.findByUser(user)).thenReturn(new HashSet<>(user.getAccounts()));
    }

    @Test
    void getAllAccountsByUsername() throws Exception {
        var name = "tester";
        var accounts = accountService.getAllAccountsByUsername();

        Assertions.assertThat(accounts.isEmpty()).isEqualTo(false);
        Assertions.assertThat(accounts.size()).isEqualTo(2);
    }

    @Test
    void addAccount() {
        var accounts = accountService.addAccount(333);
        Assertions.assertThat(accounts.isEmpty()).isEqualTo(false);
        Assertions.assertThat(accounts.size()).isEqualTo(2);

        accounts = accountService.getAllAccountsByUsername();
        Assertions.assertThat(accounts.isEmpty()).isEqualTo(false);
        Assertions.assertThat(accounts.size()).isEqualTo(2);
    }

    @Test
    void changeAccountNumber() {
        var newNumber = 876543212;
        accountService.changeAccountNumber(1, newNumber);
        var account = accountService.getAccount(1);
        Assertions.assertThat(account.getNumber()).isEqualTo(newNumber);

        var accounts = accountService.getAllAccountsByUsername();
        Assertions.assertThat(accounts.stream().anyMatch(acc -> acc.getNumber() == newNumber)).isEqualTo(true);
    }

    @Test
    void getNonExistAccount() {
        Assertions.assertThatThrownBy(() -> accountService.getAccount(13))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("no account with id");
    }

    @Test
    void addHistoryToAccount() {
        var newValue = 18555L;
        accountService.addHistoryToAccount(1, newValue);

        var histories = accountService.getAccount(1).getHistories();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(histories.size()).isEqualTo(3),
                () -> Assertions.assertThat(histories.stream().anyMatch(h -> h.getValue() == newValue))
        );

    }

    @Test
    void removeAllHistoryOfAccount() {
        accountService.removeAllHistoryOfAccount(1);
        var histories = accountService.getAccount(1).getHistories();
        Assertions.assertThat(histories.isEmpty()).isEqualTo(true);
    }
}