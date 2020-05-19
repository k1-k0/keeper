package com.electricity.keeper.service;

import com.electricity.keeper.model.User;

public interface UserService {
    User registerUser(String username, String password, String confirm, String email);

    Boolean verifyUser(String username, String password);

    String getCurrentUsername();
}
