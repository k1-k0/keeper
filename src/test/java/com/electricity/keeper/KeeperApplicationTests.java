package com.electricity.keeper;

import com.electricity.keeper.controller.AccountController;
import com.electricity.keeper.controller.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KeeperApplicationTests {


    @Autowired
    private UserController userController;

    @Autowired
    private AccountController accountController;


    @Test
    void contextLoads() throws Exception {
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(userController).isNotNull(),
                () -> Assertions.assertThat(accountController).isNotNull()
        );
    }

}
