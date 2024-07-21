package com.habittracker.tracker.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userRunner(UserRepository repository, UserService userService, CommandLineRunner habitRunner) {
        // seeds the database with dummy user data
        return args -> {
            User connor = new User(
                    "Connor",
                    "connorcliff211@gmail.com",
                    "Password1"
            );

            User one = new User(
                    "Claire55",
                    "1",
                    "1"
            );

            User alex = new User(
                    "Alex1",
                    "alexander@hotmail.com",
                    "Password1"
            );

            User james = new User(
                    "JamesJames",
                    "james1995@hotmail.com",
                    "NewPass"
            );

            User luca = new User(
                    "The_Luca",
                    "luca@hotmail.com",
                    "Pass"
            );

            User alan = new User(
                    "Alan",
                    "alan@hotmail.com",
                    "Password1"
            );

            User callum = new User(
                    "Callum",
                    "callum@hotmail.com",
                    "PassWORD1"
            );
            User david = new User(
                    "User123",
                    "user123@gmail.com",
                    "password1"
            );

            User john = new User(
                    "Naruto",
                    "naruto@outlook.com",
                    "MyPass"
            );

            User sarah = new User(
                    "Coolguy99",
                    "coolio99@hotmail.com",
                    "Password1"
            );

            User carl = new User(
                    "tech.man",
                    "t_man@hotmail.com",
                    "NewPass"
            );

            User mary = new User(
                    "hisoka",
                    "hisoka@hotmail.com",
                    "Pass"
            );

            User joseph = new User(
                    "brisbane_bandit",
                    "bbb@hotmail.com",
                    "Password1"
            );

            User holly = new User(
                    "batman",
                    "batman@hotmail.com",
                    "PassWORD1"
            );

            repository.saveAll(
                    List.of(connor, one, alex, james, luca, alan, callum,david, john, sarah, carl, mary , joseph, holly)
            );

            // uncomment for terminal ui usage
            /*
            UserManager userManager = new UserManager(repository, userService, habitRunner);
            userManager.loginMenu();

             */

        };
    }
}
