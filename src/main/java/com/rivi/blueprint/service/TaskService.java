package com.rivi.blueprint.service;

import com.rivi.blueprint.dto.Task;

public interface TaskService {
    Task createTask(Long storyId, Task task);

    void deleteTask(Long taskId);
}
