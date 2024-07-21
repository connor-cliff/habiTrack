package com.habittracker.tracker.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/friendship")
@CrossOrigin
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // Get method to fetch friendships based on the provided userId
    @GetMapping
    public List<Friendship> getFriendships(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            // If userId is provided, filter friendships by userId
            return friendshipService.getFriendshipsById(userId);
        } else {
            // If userId is not provided, return all friendships
            return friendshipService.getFriendships();
        }
    }

    // Post method to add a new friendship to the database
    @PostMapping
    public void registerNewFriendship(@RequestBody Friendship friendship) {
        friendshipService.addNewFriendship(friendship);
    }

    // Delete method to remove a friendship from the database
    @DeleteMapping(path = "{id}")
    public void deleteFriendship(@PathVariable("id") Long friendshipId) {
        friendshipService.deleteFriendship(friendshipId);
    }


}
