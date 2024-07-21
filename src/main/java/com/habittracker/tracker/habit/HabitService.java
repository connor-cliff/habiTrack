package com.habittracker.tracker.habit;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    @Autowired
    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    // Defines a GET mapping to retrieve all habits
    @GetMapping
    public List<Habit> getHabits() {
        return habitRepository.findAll();
    }

    // Add a new habit to the database
    public void addNewHabit(Habit habit) {
        habitRepository.save(habit);
    }

    // uncomment for terminal version usage
    /*
    public void deleteHabit(String habitName) {
        Optional<Habit> habitOptional = habitRepository.findHabitByName(habitName);
        if (habitOptional.isEmpty()) {
            throw new IllegalStateException("Habit with name " + habitName + " does not exist");
        }
        habitRepository.delete(habitOptional.get());
    }
     */

    // delete a habit by its ID
    public void deleteHabit(Long habitId) {
        boolean exists = habitRepository.existsById(habitId);
        if (!exists) {
            throw new IllegalStateException(
                    "Habit with id " + habitId + " does not exist");
        }
        habitRepository.deleteById(habitId);
    }

    // get habits by userId
    public List<Habit> getHabitsByUserId(Long userId) {
        return habitRepository.findHabitsByUserId(userId);
    }

    // Updates habit data of an existing habit
    @Transactional
    public void updateHabit(Long habitId, Long userId, String name, String des, LocalTime reminder, Integer streak) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() ->
                new IllegalStateException("habit with id " + habitId + " does not exist"));

        // update userId
        if (des != null && des.length() > 0 && !Objects.equals(habit.getDescription(), des)) {
            habit.setDescription(des);
        }
        // update name
        if (name != null && name.length() > 0 && !Objects.equals(habit.getName(), name)) {
            habit.setName(name);
        }
        // update description
        if (des != null && des.length() > 0 && !Objects.equals(habit.getDescription(), des)) {
            habit.setDescription(des);
        }
        if (reminder != null && !Objects.equals(habit.getReminder(), reminder)) {
            habit.setReminder(reminder);
        }
        if (streak != null) {
            habit.setStreak(streak);
        }
    }

    // updates the streak value of a habit
    @Transactional
    public void increaseStreak(Long habitId, int newStreak) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new IllegalStateException(
                        "habit with id " + habitId + " does not exist"));

        if (newStreak > 0) {
            habit.setStreak(newStreak);
        }
    }
}
