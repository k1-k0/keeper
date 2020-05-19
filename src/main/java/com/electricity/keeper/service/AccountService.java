package com.electricity.keeper.service;

import com.electricity.keeper.model.Account;

import java.util.NoSuchElementException;
import java.util.Set;

public interface AccountService {
    Set<Account> getAllAccountsByUsername() throws NoSuchElementException;

    Set<Account> addAccount(long number) throws NoSuchElementException;

    Account changeAccountNumber(long accountId, long number) throws NoSuchElementException;

    Account getAccount(long accountId) throws NoSuchElementException;

    Account addHistoryToAccount(long accountId, long value) throws NoSuchElementException; //TODO: What about photo?

    Account removeAllHistoryOfAccount(long accountId) throws NoSuchElementException;
}
