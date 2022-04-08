package com.rivi.blueprint.service.impl;

import com.rivi.blueprint.dao.TaskEntity;
import com.rivi.blueprint.dao.UserEntity;
import com.rivi.blueprint.dao.UserStoryEntity;
import com.rivi.blueprint.dto.Task;
import com.rivi.blueprint.repository.TaskRepository;
import com.rivi.blueprint.repository.UserRepository;
import com.rivi.blueprint.repository.UserStoryRepository;
import com.rivi.blueprint.service.TaskService;
import com.rivi.blueprint.utils.exception.BadRequestException;
import com.rivi.blueprint.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    UserStoryRepository userStoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;


    @Override
    public Task createTask(Long storyId, Task task) {
        // fetch user story
        Optional<UserStoryEntity> existingUserStory = userStoryRepository.findById(storyId);
        if (!existingUserStory.isPresent()) {
            throw new NotFoundException("User story with given id does not exist");
        }

        TaskEntity taskEntity =
                TaskEntity.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .createdDate(new Date())
                        .userStoryEntity(existingUserStory.get())
                        .build();

        // assign user
        if (null != task.getUser()) {
            updateAssignee(task, taskEntity);
        }

        taskEntity = taskRepository.save(taskEntity);
        task.setId(taskEntity.getId());
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        try {
            taskRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Given task id does not exist");
        }
    }

    private void updateAssignee(Task task, TaskEntity taskEntity) {
        if (null != task.getUser().getId()) {
            Optional<UserEntity> existingUser = userRepository.findById(task.getUser().getId());
            if (!existingUser.isPresent()) {
                throw new BadRequestException("User with given id does not exist");
            }
            taskEntity.setUserEntity(existingUser.get());
        } else {
            try {
                UserEntity userEntity = UserEntity.builder()
                        .email(task.getUser().getEmail())
                        .name(task.getUser().getName())
                        .type(task.getUser().getType())
                        .build();
                userEntity = userRepository.save(userEntity);

                taskEntity.setUserEntity(userEntity);
                task.getUser().setId(userEntity.getId());
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("User with same name and email already exists");
            }
        }
    }
}
