package com.rivi.blueprint.service.impl;

import com.rivi.blueprint.dao.TaskEntity;
import com.rivi.blueprint.dao.UserEntity;
import com.rivi.blueprint.dao.UserStoryEntity;
import com.rivi.blueprint.dto.Task;
import com.rivi.blueprint.dto.User;
import com.rivi.blueprint.dto.UserStory;
import com.rivi.blueprint.repository.UserRepository;
import com.rivi.blueprint.repository.UserStoryRepository;
import com.rivi.blueprint.service.UserStoryService;
import com.rivi.blueprint.utils.exception.BadRequestException;
import com.rivi.blueprint.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService {

    @Autowired
    UserStoryRepository userStoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserStory> getAllUserStories() {
        List<UserStoryEntity> userStoryEntities = userStoryRepository.findAll();

        List<UserStory> userStories = userStoryEntities.stream().map(userStoryEntity ->
                buildUserStoryDtoFromEntity(userStoryEntity)).collect(Collectors.toList());
        return userStories;
    }

    @Override
    public UserStory createUserStory(UserStory userStory) {

        UserStoryEntity userStoryEntity =
                UserStoryEntity.builder()
                        .title(userStory.getTitle())
                        .description(userStory.getDescription())
                        .dueDate(userStory.getDueDate())
                        .status(userStory.getStatus())
                        .createdDate(new Date())
                        .build();

        // assign user
        if (null != userStory.getUser()) {
            updateAssignee(userStory, userStoryEntity);
        }

        userStoryEntity = userStoryRepository.save(userStoryEntity);
        userStory.setId(userStoryEntity.getId());
        return userStory;
    }

    @Override
    public void deleteUserStory(Long storyId) {
        try {
            userStoryRepository.deleteById(storyId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Given story id does not exist");
        }
    }

    @Override
    public UserStory updateUserStory(Long storyId, UserStory userStory) {
        if (userStory.isAllFieldsNull(userStory)) {
            throw new BadRequestException("Nothing to update");
        }

        // fetch user story
        Optional<UserStoryEntity> existingUserStory = userStoryRepository.findById(storyId);
        if (!existingUserStory.isPresent()) {
            throw new NotFoundException("User story with given id does not exist");
        }

        UserStoryEntity userStoryEntity = existingUserStory.get();

        // assign user
        if (null != userStory.getUser()) {
            updateAssignee(userStory, userStoryEntity);
        }

        if (null != userStory.getStatus()) {
            userStoryEntity.setStatus(userStory.getStatus());
        }

        if (null != userStory.getTitle()) {
            userStoryEntity.setTitle(userStory.getTitle());
        }

        if (null != userStory.getDescription() && userStory.getDescription().isBlank()) {
            userStoryEntity.setDescription(userStory.getDescription());
        } else if (null != userStory.getDescription()) {
            userStoryEntity.setDescription(userStory.getDescription());
        }

        if (null != userStory.getDueDate()) {
            userStoryEntity.setDueDate(userStory.getDueDate());
        }

        userStoryEntity.setUpdatedDate(new Date());
        userStoryRepository.save(userStoryEntity);
        return buildUserStoryDtoFromEntity(userStoryEntity);
    }

    private void updateAssignee(UserStory userStory, UserStoryEntity userStoryEntity) {
        if (null != userStory.getUser().getId()) {
            Optional<UserEntity> existingUser = userRepository.findById(userStory.getUser().getId());
            if (!existingUser.isPresent()) {
                throw new BadRequestException("User with given id does not exist");
            }
            userStoryEntity.setUserEntity(existingUser.get());
        } else {
            try {
                UserEntity userEntity = UserEntity.builder()
                        .email(userStory.getUser().getEmail())
                        .name(userStory.getUser().getName())
                        .type(userStory.getUser().getType())
                        .build();
                userEntity = userRepository.save(userEntity);

                userStoryEntity.setUserEntity(userEntity);
                userStory.getUser().setId(userEntity.getId());
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("User with same name and email already exists");
            }
        }
    }

    private UserStory buildUserStoryDtoFromEntity(UserStoryEntity userStoryEntity) {
        return UserStory.builder()
                .id(userStoryEntity.getId())
                .title(userStoryEntity.getTitle())
                .description(userStoryEntity.getDescription())
                .dueDate(userStoryEntity.getDueDate())
                .status(userStoryEntity.getStatus())
                .user(null != userStoryEntity.getUserEntity() ? User.builder()
                        .email(userStoryEntity.getUserEntity().getEmail())
                        .name(userStoryEntity.getUserEntity().getName())
                        .id(userStoryEntity.getUserEntity().getId())
                        .type(userStoryEntity.getUserEntity().getType())
                        .build() : null)
                .taskList(null != userStoryEntity.getTasks() ? buildTaskListFromEntity(userStoryEntity.getTasks()) : null)
                .build();
    }

    private List<Task> buildTaskListFromEntity(Set<TaskEntity> taskEntities) {
        List<Task> taskList = taskEntities.stream().map(taskEntity ->
                Task.builder()
                        .id(taskEntity.getId())
                        .title(taskEntity.getTitle())
                        .description(taskEntity.getDescription())
                        .user(null != taskEntity.getUserEntity() ? User.builder()
                                .email(taskEntity.getUserEntity().getEmail())
                                .name(taskEntity.getUserEntity().getName())
                                .id(taskEntity.getUserEntity().getId())
                                .type(taskEntity.getUserEntity().getType())
                                .build() : null)
                        .build()).collect(Collectors.toList());
        return taskList;
    }
}
