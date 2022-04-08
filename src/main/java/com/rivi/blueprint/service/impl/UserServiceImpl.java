package com.rivi.blueprint.service.impl;

import com.rivi.blueprint.dao.UserEntity;
import com.rivi.blueprint.dto.User;
import com.rivi.blueprint.repository.UserRepository;
import com.rivi.blueprint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> searchUsers(String query) {
        List<UserEntity> userEntities;
        if (null == query || (null != query && query.isBlank())) {
            userEntities = userRepository.findAll();
        } else {
            userEntities = userRepository.findBySearchQuery(query);
        }

        return userEntities.stream().map(userEntity ->
                User.builder()
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .id(userEntity.getId())
                        .type(userEntity.getType())
                        .build()).collect(Collectors.toList());
    }
}
