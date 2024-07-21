package com.habittracker.tracker.interfaces;

public interface UserInterface {
    void displayHomeMenu();
    void displayCreateHabitMenu();

    void displayEditHabitMenu(String habitName);

    void displayHabits();
    void displayLogin();
    void displaySignUp();

}
