package com.electricity.keeper.service;

import com.electricity.keeper.model.Account;
import com.electricity.keeper.model.History;
import com.electricity.keeper.repository.AccountRepository;
import com.electricity.keeper.repository.HistoryRepository;
import com.electricity.keeper.repository.PhotoRepository;
import com.electricity.keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Set<Account> getAllAccountsByUsername() throws NoSuchElementException {
        var username = userService.getCurrentUsername();
        var result = userRepository.findByUsername(username);
        if (result.isPresent()) {
            var user = result.get();
            return accountRepository.findByUser(user);
        } else {
            throw new NoSuchElementException("There are no user with username: " + username);
        }
    }

    @Override
    public Set<Account> addAccount(long number) throws NoSuchElementException {
        var username = userService.getCurrentUsername();
        var result = userRepository.findByUsername(username);
        if (result.isPresent()) {
            var user = result.get();
            user.addAccount(new Account(number));
            userRepository.save(user);

            return accountRepository.findByUser(user);
        } else {
            throw new NoSuchElementException("There are no user with username: " + username);
        }
    }

    @Override
    public Account changeAccountNumber(long accountId, long number) throws NoSuchElementException {
        var result = accountRepository.findById(accountId);

        if (result.isPresent()) {
            var account = result.get();
            account.setNumber(number);

            accountRepository.save(account);

            return account;
        } else {
            throw new NoSuchElementException("There are no account with id: " + accountId);
        }
    }

    @Override
    public Account getAccount(long accountId) throws NoSuchElementException {
        var account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new NoSuchElementException("There are no account with id: " + accountId);
        }
    }

    @Override
    public Account addHistoryToAccount(long accountId, long value) throws NoSuchElementException {
        var account = accountRepository.findById(accountId);

        account.ifPresent(acc -> {
            var history = new History(value);
            acc.addHistory(history);
            accountRepository.save(acc);
        });

        if (account.isPresent())
            return account.get();
        else {
            throw new NoSuchElementException("There are no account with id: " + accountId);
        }
    }

    @Override
    public Account removeAllHistoryOfAccount(long accountId) throws NoSuchElementException {
        var accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            var account = accountOptional.get();
            account.removeAllHistory();
            accountRepository.save(account);
            return account;
        } else {
            throw new NoSuchElementException("There are no account with id: " + accountId);
        }
    }
}
