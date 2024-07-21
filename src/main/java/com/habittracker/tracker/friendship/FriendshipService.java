package com.habittracker.tracker.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class FriendshipService {

        private final com.habittracker.tracker.friendship.FriendshipRepository friendshipRepository;

        @Autowired
        public FriendshipService(com.habittracker.tracker.friendship.FriendshipRepository friendshipRepository) {
            this.friendshipRepository = friendshipRepository;
        }

        // Defines a GET mapping to retrieve all friendships
        @GetMapping
        public List<Friendship> getFriendships() {
            return friendshipRepository.findAll();
        }

        // Add a new friendship to the database
        public void addNewFriendship(Friendship friendship) {
            friendshipRepository.save(friendship);
        }

        // delete a friendship by its ID
        public void deleteFriendship(Long friendshipId) {
            boolean exists = friendshipRepository.existsById(friendshipId);
            if (!exists) {
                throw new IllegalStateException(
                        "Friendship with id " + friendshipId + " does not exist");
            }
            friendshipRepository.deleteById(friendshipId);
        }

        // Get friendships by userId
        public List<Friendship> getFriendshipsById(Long id) {
            return friendshipRepository.findFriendshipsById(id);
        }

    }

