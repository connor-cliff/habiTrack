package com.habittracker.tracker.controllers;

import com.habittracker.tracker.habit.Habit;
import com.habittracker.tracker.habit.HabitRepository;
import com.habittracker.tracker.habit.HabitService;
import com.habittracker.tracker.ui.TerminalUI;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class represents the AppManager responsible for handling the terminal UI (TUI) of the Habit Tracker application.
 * It provides methods to manage habits, display menus, and interact with the user through the terminal interface.
 */
public class AppManager {

    private HabitRepository repository;
    private HabitService habitService;
    static TerminalUI tui = new TerminalUI();

    /**
     * Constructor to create an instance of AppManager with a HabitRepository.
     *
     * @param repository The HabitRepository to use for data storage.
     */
    public AppManager(HabitRepository repository) {
        this.repository = repository;
    }

    /**
     * Constructor to create an instance of AppManager with both HabitRepository and HabitService.
     *
     * @param repository   The HabitRepository to use for data storage.
     * @param habitService The HabitService to use for business logic.
     */
    public AppManager(HabitRepository repository, HabitService habitService) {
        this.repository = repository;
        this.habitService = habitService;
    }

    /**
     * This method displays the home menu to the user and handles user input for different options.
     * It loops back to the console until the user chooses to exit the program.
     *
     * @throws NullPointerException if the HabitService or TerminalUI is not initialized.
     */
    public void home() throws NullPointerException {

        // Loops the menu back to the console until the user chooses to exit the program.
        boolean done = false;
        while (!done) {

            tui.displayHomeMenu();
            System.out.print("Enter option: ");

            Scanner input = new Scanner(System.in);
            char option = input.next().charAt(0);

            // Consumes \n after option
            input.nextLine();

            switch (option) {
                case '1' -> createNewHabit();
                case '2' -> printHabitsToConsole();
                case '3' -> editHabit();
                case '4' -> increaseStreak();
                case '5' -> System.out.println("navigation menu - to implement");
                case 'f' -> {
                    done = true;
                    System.out.println("Exiting program...");
                }
                default -> System.out.println("Invalid input - Please try one of the menu options.");
            }
        }
    }

    /**
     * This method creates a new Habit by getting input from the user and saves it to the repository.
     * It ensures mandatory fields are provided before saving the new Habit.
     */
    public void createNewHabit() {

        // Create a new instance of Habit
        Habit habit = new Habit();
        Long userId = 0L; //placeholder
        String checkedName = null;
        String checkedDes = null;
        LocalTime checkedTime = null;
        int streak = 0;

        boolean done = false;
        while (!done) {

            // Prints menu to console
            tui.displayCreateHabitMenu();

            Scanner input = new Scanner(System.in);
            char option = input.next().charAt(0);

            // Consumes \n after option
            input.nextLine();

            switch (option) {
                case '1' -> {
                    System.out.print("Enter name: ");
                    String inputName = input.nextLine();
                    checkedName = checkName(inputName);
                }
                case '2' -> {
                    System.out.print("Enter description: ");
                    String inputDes = input.nextLine();
                    checkedDes = checkDescription(inputDes);
                }
                case '3' -> {
                    System.out.print("Enter reminder time (in 24-hour format, HH:mm): ");
                    String inputTime = input.nextLine();
                    checkedTime = checkReminder(inputTime);
                }
                case '4' -> {
                    // Checks habit data has been provided
                    if ((Objects.equals(habit.getName(), "No name provided"))) {
                        System.out.println("Please complete all mandatory forms before saving.");
                    } else {
                        System.out.println("Saving habit...");
                        // Adds habit to the list
                        habit = new Habit(userId, checkedName, checkedDes, checkedTime, streak);
                        repository.save(habit);
                        done = true;
                    }
                }
                case 'f' -> {
                    done = true;
                    System.out.println("Going back...");
                }
                default -> System.out.println("Invalid input - Please try one of the menu options.");
            }
        }
    }

    /**
     * This method checks if the provided name is valid and returns the checked name.
     *
     * @param name The name to be checked.
     * @return The checked name, or null if the provided name is invalid.
     */
    private String checkName(String name) {
        String checkedName = null;

        // Validates the user input
        if (!(name == null || name.trim().isEmpty() || name.equals("\n"))) {
            checkedName = name;
            System.out.println("Chosen name: " + name);

        } else {
            System.out.println("Invalid name entered. Please try again.");
        }
        return checkedName;
    }

    /**
     * This method checks if the provided description is valid and returns the checked description.
     *
     * @param des The description to be checked.
     * @return The checked description, or an empty string if the provided description is invalid.
     */
    private String checkDescription(String des) {
        String checkedDes = "";

        // Validates user input
        if (!(des.trim().isEmpty() || des.equals("\n"))) {
            checkedDes = des;
            System.out.println("Chosen description: " + checkedDes);

        } else {
            System.out.println("No description chosen");
        }
        return checkedDes;
    }

    /**
     * This method checks if the provided time is valid and returns the checked time as LocalTime.
     *
     * @param time The time to be checked in the format "HH:mm".
     * @return The checked time as LocalTime.
     */
    private LocalTime checkReminder(String time) {
        LocalTime checkedTime = LocalTime.of(0, 0, 0);

        // Validates user input
        if (time == null || time.trim().isEmpty() || time.equals("\n")) {

            System.out.println("No reminder set");

            // Checks user input matches HH:mm format
        } else if (!time.matches("^\\d{2}:\\d{2}$")) {
            System.out.println("Invalid input. Please try again.");

        } else {
            // Parse user input into hours and minutes
            String[] timeParts = time.split(":");
            int hours = Integer.parseInt(timeParts[0]);
            int mins = Integer.parseInt(timeParts[1]);

            // Validate the user input
            if (hours < 0 || hours > 23 || mins < 0 || mins > 59) {
                System.out.println("Invalid time. Please enter a valid time in 24-hour format.");

            } else {
                // Set the reminder
                checkedTime = LocalTime.of(hours, mins);
                System.out.println("Daily reminder set for: " + checkedTime);
            }
        }
        return checkedTime;
    }

    /**
     * This method displays the list of habits and their details to the console.
     */
    public void printHabitsToConsole() {
        tui.displayHabits();
        List<Habit> habits = habitService.getHabits();
        printHabitValues(habits);
    }

    /**
     * This method prints the habit details in the habit list to the console.
     *
     * @param habits The list of habits to print.
     */
    private void printHabitValues(List<Habit> habits) {
        // Print the habit values to the console
        for (Habit habit : habits) {
            System.out.println("Habit Name: " + habit.getName());
            System.out.println("Description: " + habit.getDescription());
            System.out.println("Reminder: " + habit.getReminder());
            System.out.println("Streak: " + habit.getStreak());
            System.out.println();
        }
    }

    /**
     * This method displays the habit edit menu to the user and handles the editing of existing habits.
     */
    public void editHabit() {

        // Displays existing habits
        printHabitsToConsole();

        System.out.print("Select an existing habit: ");
        Scanner input = new Scanner(System.in);
        String habitName = input.nextLine();

        Optional<Habit> habitOptional = habitService.getHabits().stream()
                .filter(habit -> habit.getName().equalsIgnoreCase(habitName))
                .findFirst();

        if (habitOptional.isPresent()) {
            Habit habit = habitOptional.get();

            Long userId = 0L; //placeholder
            String newName = null;
            String newDes = null;
            LocalTime newTime = null;
            Integer newStreak = null;

            // Loops the menu back to the console until the user chooses to exit the program.
            boolean done = false;
            while (!done) {

                tui.displayEditHabitMenu(habitName);

                char option = input.next().charAt(0);
                // Consumes \n after option
                input.nextLine();

                switch (option) {
                    case '1' -> {
                        System.out.print("Enter new name: ");
                        newName = input.nextLine();
                    }
                    case '2' -> {
                        System.out.print("Enter new description: ");
                        newDes = input.nextLine();
                    }
                    case '3' -> {
                        System.out.print("Enter new reminder time (in 24-hour format, HH:mm): ");
                        String inputTime = input.nextLine();
                        newTime = checkReminder(inputTime);
                    }
                    case '4' -> {
                        System.out.print("Enter new streak: ");
                        newStreak = Integer.parseInt(input.nextLine());
                    }
                    case '5' -> {
                        habitService.deleteHabit(habit.getHabitId());
                        done = true;
                        System.out.println("Habit deleted. Returning to main menu.");
                    }
                    case '6' -> {
                        habitService.updateHabit(habit.getHabitId(), userId, newName, newDes, newTime, newStreak);
                        System.out.println("Changes saved");
                    }
                    case 'f' -> {
                        done = true;
                        System.out.println("Going back...");
                    }
                    default -> System.out.println("Invalid input - Please try one of the menu options.");
                }
            }

        } else {
            System.out.println("Habit not found. Please try again");
        }
    }

    /**
     * This method increases the streak of an existing habit by 1 and updates it in the repository.
     */
    public void increaseStreak() {
        // Display existing habits
        printHabitsToConsole();

        // Prompt the user to select an existing habit
        System.out.print("Select an existing habit: ");
        Scanner input = new Scanner(System.in);
        String habitName = input.nextLine();

        // Find the habit with the specified name
        Optional<Habit> habitOptional = habitService.getHabits().stream()
                .filter(habit -> habit.getName().equalsIgnoreCase(habitName))
                .findFirst();

        if (habitOptional.isPresent()) {
            Habit habit = habitOptional.get();

            // increment the current streak by 1
            int newStreak = habit.getStreak();
            newStreak++;

            // Update the habit's streak value
            habitService.increaseStreak(habit.getHabitId(), newStreak);
            System.out.println("Streak increased by 1!");
        } else {
            System.out.println("Habit not found. Please try again.");
        }
    }
}