package com.rivi.blueprint.controller;

import com.rivi.blueprint.dto.User;
import com.rivi.blueprint.service.UserService;
import com.rivi.blueprint.utils.ApiResponse;
import com.rivi.blueprint.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constant.BASE_URI)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = Constant.USERS )
    public ResponseEntity<ApiResponse<List<User>>> searchUsers(
            @RequestParam(required = false) String query
    ) {
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), Constant.SUCCESS,
                        userService.searchUsers(query))
        );
    }
}
