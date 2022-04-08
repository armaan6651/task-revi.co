package com.rivi.blueprint.service;

import com.rivi.blueprint.dto.UserStory;

import java.util.List;

public interface UserStoryService {
    List<UserStory> getAllUserStories();

    UserStory createUserStory(UserStory userStory);

    void deleteUserStory(Long storyId);

    UserStory updateUserStory(Long storyId, UserStory userStory);
}
