package com.habittracker.tracker.habit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/habit")
@CrossOrigin
public class HabitController {

    private final HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // Get method to fetch habits based on the provided userId
    @GetMapping
    public List<Habit> getHabits(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            // If userId is provided, filter habits by userId
            return habitService.getHabitsByUserId(userId);
        } else {
            // If userId is not provided, return all habits
            return habitService.getHabits();
        }
    }

    // Post method to add a new habit to the database
    @PostMapping
    public void registerNewHabit(@RequestBody Habit habit) {
        habitService.addNewHabit(habit);
    }

    // Delete method to remove a habit from the database
    @DeleteMapping(path = "{habitId}")
    public void deleteHabit(@PathVariable("habitId") Long habitId) {
        habitService.deleteHabit(habitId);
    }

    // Put mapping to update habit data
    @PutMapping(path = "{habitId}")
    public void updateHabit(
            @PathVariable("habitId") Long habitId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalTime reminder,
            @RequestParam(required = false) Integer streak
            ){
        habitService.updateHabit(habitId, userId, name, description, reminder, streak);
    }
}
