package com.habittracker.tracker.habit;

import com.habittracker.tracker.controllers.AppManager;
import com.habittracker.tracker.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.List;

@Configuration
public class HabitConfig {

    @Bean
    CommandLineRunner HabitRunner(HabitRepository repository, HabitService habitService) {
        // Seeds the database with dummy habit data
        return args -> {
            Habit habit0 = new Habit(
                    2L,
                    "Clean the dishes",
                    "",
                    LocalTime.of(11, 11),
                    5
            );

            Habit habit1 = new Habit(
                    2L,
                    "Daily walk",
                    "Walk for at least 30 minutes every day",
                    LocalTime.of(9, 15),
                    12
            );

            Habit habit2 = new Habit(
                    1L,
                    "Read my book",
                    "Read at least 3 pages a day",
                    LocalTime.of(10, 30),
                    8
            );

            Habit habit3 = new Habit(
                    2L,
                    "Study for 1 hour",
                    "",
                    LocalTime.of(10, 30),
                    10
            );
            Habit habit4 = new Habit(
                    1L,
                    "Workout",
                    "workout for 45 minutes",
                    LocalTime.of(17, 45),
                    18
            );

            repository.saveAll(
                    List.of(habit0, habit1, habit2, habit3, habit4)
            );

/*
            // uncomment to activate the terminal UI
            AppManager appManager = new AppManager(repository, habitService);
            appManager.home();

 */
        };
    }
}