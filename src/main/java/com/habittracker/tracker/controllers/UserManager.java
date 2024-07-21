package com.habittracker.tracker.controllers;

import com.habittracker.tracker.ui.TerminalUI;
import com.habittracker.tracker.user.User;
import com.habittracker.tracker.user.UserRepository;
import com.habittracker.tracker.user.UserService;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;
import java.util.Scanner;

/**
 * UserManager is a class responsible for managing user interactions and operations in the terminal UI (TUI)
 * version of the Habit Tracker application.
 * It allows users to login or sign up, perform actions related to their habits, and exit the program.
 */
public class UserManager {
private UserRepository repository;
private UserService userService;
private CommandLineRunner habitRunner;
static TerminalUI tui = new TerminalUI();

    /**
     * Constructor for UserManager class.
     *
     * @param repository   The UserRepository used to access user data.
     * @param userService  The UserService used to perform user related operations.
     * @param habitRunner  The CommandLineRunner used to run habit related functionality.
     */
    public UserManager(UserRepository repository, UserService userService, CommandLineRunner habitRunner) {
        this.repository = repository;
        this.userService = userService;
        this.habitRunner = habitRunner;
    }

    /**
     * Displays the login menu and handles user input to perform login or sign up operations.
     *
     * @throws Exception if any exceptional scenario occurs during the execution.
     */
    public void loginMenu() throws Exception {

        String email = "";
        String password = "";

        // Loops the menu back to the console until the user chooses to exit the program.
        boolean done = false;
        while (!done) {

            tui.displayLogin();
            System.out.print("Enter option: ");

            Scanner input = new Scanner(System.in);
            char option = input.next().charAt(0);

            // Consumes \n after option
            input.nextLine();

            switch(option) {
                case '1' -> {
                    System.out.print("Enter email: ");
                    email = input.nextLine();
                }
                case '2' -> {
                    System.out.print("Enter password: ");
                    password = input.nextLine();
                }
                case '3' -> {
                    userService.login(email, password);
                    habitRunner.run();
                }
                case '4' -> signupMenu();
                case 'f' -> {
                    done = true;
                    System.out.println("Exiting program...");
                }
                default -> System.out.println("Invalid input - Please try one of the menu options.");

            }
        }
    }

    /**
     * Attempts to perform user login using the provided email and password.
     * Displays appropriate messages based on the login success or failure.
     *
     * @param email    The email provided by the user for login.
     * @param password The password provided by the user for login.
     * @throws Exception if any exceptional scenario occurs during the execution.
     */
    private void login(String email, String password) throws Exception {
        Optional<User> optionalUser = repository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPass().equals(password)) {
                // Credentials match an existing user
                System.out.println("call habitRunner here");
                habitRunner.run();
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        } else {
            System.out.println("User not found. Please try again.");
        }
    }

    /**
     * Displays the sign up menu and handles user input to perform the sign up operations.
     *
     * @throws Exception if any exceptional scenario occurs during the execution.
     */
    public void signupMenu() throws Exception {

        String name = "";
        String email = "";
        String pass = "";

        // Loops the menu back to the console until the user chooses to exit the program.
        boolean done = false;
        while (!done) {

            tui.displaySignUp();
            System.out.print("Enter option: ");

            Scanner input = new Scanner(System.in);
            char option = input.next().charAt(0);

            // Consumes \n after option
            input.nextLine();

            switch(option) {
                case '1' -> {
                    System.out.print("Enter name:");
                    name = input.nextLine();
                }
                case '2' -> {
                    System.out.print("Enter email: ");
                    email = input.nextLine();
                }
                case '3' -> {
                    System.out.print("Enter password: ");
                    pass = input.nextLine();
                }
                case '4' -> signup(name, email, pass);
                case '5' -> loginMenu();
                case 'f' -> {
                    done = true;
                    System.out.println("Exiting program...");
                }
                default -> System.out.println("Invalid input - Please try one of the menu options.");

            }
        }
    }

    /**
     * Performs user sign up with the provided name, email and password.
     * Displays appropriate messages based on the sign up success or failure.
     *
     * @param name   The name provided by the user for sign-up.
     * @param email  The email provided by the user for sign-up.
     * @param pass   The password provided by the user for sign-up.
     * @throws Exception if any exceptional scenario occurs during the execution.
     */
    private void signup(String name, String email, String pass) throws Exception {
        if (name.isEmpty() || pass.isEmpty() || email.isEmpty()) {
            System.out.println("Please complete all fields.");
        } else {
            User newUser = new User(name, email, pass);
            userService.addNewUser(newUser);
            loginMenu();
        }
    }
}
