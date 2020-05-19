package com.electricity.keeper.repository;

import com.electricity.keeper.model.Account;
import com.electricity.keeper.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Set<Account> findByUser(User user);
}
