package com.rivi.blueprint.service;

import com.rivi.blueprint.dto.User;

import java.util.List;

public interface UserService {
    List<User> searchUsers(String query);
}
