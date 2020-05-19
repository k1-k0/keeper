package com.electricity.keeper.service;

import com.electricity.keeper.model.ERole;
import com.electricity.keeper.model.Role;
import com.electricity.keeper.model.User;
import com.electricity.keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    final private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User registerUser(String username, String password, String confirm, String email) throws EntityExistsException {
        var usernameExist = userRepository.findByUsername(username);
        var emailExist = userRepository.findByEmail(email);

        if (emailExist.isPresent()) {
            throw new EntityExistsException("There is an user with that email: " + email);
        } else if (usernameExist.isPresent()) {
            throw new EntityExistsException("There is an user with that username: " + username);
        } else if (!password.contentEquals(confirm)) {
            throw new EntityExistsException("There is an different passwords");
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRole(new Role(ERole.USER));
            return userRepository.save(user);
        }
    }

    @Override
    public Boolean verifyUser(String username, String password) {
        var userOptional = userRepository.findByUsername(username);
        return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword())).isPresent();
    }

    @Override
    public String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentPrincipalName = authentication.getName();
        var principal = userDetailsService.loadUserByUsername(currentPrincipalName);
        return principal.getUsername();
    }
}
