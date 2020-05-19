package com.electricity.keeper.controller;

import com.electricity.keeper.repository.AccountRepository;
import com.electricity.keeper.repository.UserRepository;
import com.electricity.keeper.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping(path = "/accounts")
    public ResponseEntity<?> getAccounts() {
        try {
            var account = accountService.getAllAccountsByUsername();
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<?> addAccount(@RequestParam Long number) {
        try {
            var account = accountService.addAccount(number);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id) {
        try {
            var account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/account/{id}")
    public ResponseEntity<?> changeAccountNumber(@PathVariable long id,
                                                 @RequestParam long number) {
        try {
            var account = accountService.changeAccountNumber(id, number);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/account/{id}/history")
    public ResponseEntity<?> addNewHistoryToAccount(@PathVariable long id,
                                                    @RequestParam long value) {
        try {
            var account = accountService.addHistoryToAccount(id, value);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/account/{id}/history")
    public ResponseEntity<?> deleteAllAccountHistory(@PathVariable long id) {
        try {
            var account = accountService.removeAllHistoryOfAccount(id);
            return ResponseEntity.ok(account);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
