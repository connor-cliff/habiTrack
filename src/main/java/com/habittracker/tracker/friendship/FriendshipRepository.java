package com.habittracker.tracker.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Fetch all friendships for a specific user (user1id or user2id)
    @Query("SELECT f FROM Friendship f WHERE f.user1Id = ?1 OR f.user2Id = ?1")
    List<Friendship> findFriendshipsById(Long id);

}
