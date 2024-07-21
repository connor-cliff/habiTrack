package com.habittracker.tracker.ui;

import com.habittracker.tracker.habit.Habit;
import com.habittracker.tracker.interfaces.UserInterface;

/**
 * Prints terminal UI menus to console
 */
public class TerminalUI implements UserInterface {
    @Override
    public void displayHomeMenu() {
        System.out.println("""
                                 
                    --------------------------------
                     Home Menu
                    --------------------------------
                    1 - Create new habit
                    2 - View habits
                    3 - Edit habit
                    4 - Habit streak +1
                    5 - Navigation menu
                    f - Exit program
                    --------------------------------""");
    }


    @Override
    public void displayCreateHabitMenu() {
        System.out.println("""   
                      
                    --------------------------------
                     Create new habit
                    --------------------------------
                    1 - Name (mandatory)
                    2 - Description
                    3 - Reminder
                    4 - Complete
                    f - Go back
                    --------------------------------""");
        System.out.print("Enter option: ");

    }

    @Override
    public void displayEditHabitMenu(String habitName) {
        System.out.println("""
                      
                    --------------------------------
                     Editing habit:\040""" + habitName);
        System.out.println("""
                    --------------------------------
                    1 - Name
                    2 - Description
                    3 - Reminder
                    4 - Streak
                    5 - Delete habit
                    6 - Save changes
                    f - Go back
                    --------------------------------""");
        System.out.print("Enter option: ");
    }

    @Override
    public void displayHabits() {
        System.out.println("""
                            
                            --------------------------------
                             Viewing habit list
                            --------------------------------""");
    }

    @Override
    public void displayLogin() {
        System.out.println("""
                
                --------------------------------
                 Login
                --------------------------------
                1 - Email
                2 - Password
                3 - Login
                4 - Sign-up
                f - Exit program
                --------------------------------""");
    }

    @Override
    public void displaySignUp() {
        System.out.println("""
                
                --------------------------------
                 Sign-up
                --------------------------------
                1 - Name
                2 - Email
                3 - Password
                4 - Sign up
                5 - Login
                f - Exit program
                --------------------------------""");
    }
}
