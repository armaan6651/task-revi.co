package com.rivi.blueprint.controller;

import com.rivi.blueprint.dto.Task;
import com.rivi.blueprint.service.TaskService;
import com.rivi.blueprint.utils.ApiResponse;
import com.rivi.blueprint.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constant.BASE_URI)
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping(value = Constant.STORY + "/{storyId}" + Constant.TASK )
    public ResponseEntity<ApiResponse<Task>> createTask(
            @RequestBody @Valid Task task,
            @PathVariable Long storyId) {
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Constant.SUCCESS,
                        taskService.createTask(storyId, task))
        );
    }

    @DeleteMapping(value = Constant.TASK + "/{taskId}" )
    public ResponseEntity<ApiResponse> deleteTask(
            @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), Constant.SUCCESS,
                        null)
        );
    }
}
