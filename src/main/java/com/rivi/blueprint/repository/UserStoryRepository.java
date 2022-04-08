package com.rivi.blueprint.repository;

import com.rivi.blueprint.dao.UserStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, Long> {
}
