package com.rivi.blueprint.controller;

import com.rivi.blueprint.dto.UserStory;
import com.rivi.blueprint.service.UserStoryService;
import com.rivi.blueprint.utils.ApiResponse;
import com.rivi.blueprint.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constant.BASE_URI)
public class UserStoryController {

    @Autowired
    UserStoryService userStoryService;

    @GetMapping(value = Constant.STORY )
    public ResponseEntity<ApiResponse<List<UserStory>>> getAllUserStories() {
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Constant.SUCCESS,
                        userStoryService.getAllUserStories())
        );
    }

    @PostMapping(value = Constant.STORY )
    public ResponseEntity<ApiResponse<UserStory>> createUserStory(
            @RequestBody @Valid UserStory userStory) {
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Constant.SUCCESS,
                        userStoryService.createUserStory(userStory))
        );
    }

    @PatchMapping(value = Constant.STORY + "/{storyId}" )
    public ResponseEntity<ApiResponse<UserStory>> updateUserStory(
            @RequestBody UserStory userStory,
            @PathVariable Long storyId) {
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Constant.SUCCESS,
                        userStoryService.updateUserStory(storyId, userStory))
        );
    }

    @DeleteMapping(value = Constant.STORY + "/{storyId}" )
    public ResponseEntity<ApiResponse> deleteUserStory(
            @PathVariable Long storyId) {
        userStoryService.deleteUserStory(storyId);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), Constant.SUCCESS,
                        null)
        );
    }
}
