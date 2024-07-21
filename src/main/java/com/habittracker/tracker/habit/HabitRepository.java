package com.habittracker.tracker.habit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    // Fetches habit data based on provided userID
    @Query("SELECT h FROM Habit h WHERE h.userId = ?1")
    List<Habit> findHabitsByUserId(Long userId);


}
