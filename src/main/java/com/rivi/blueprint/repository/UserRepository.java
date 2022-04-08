package com.rivi.blueprint.repository;

import com.rivi.blueprint.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE (:query is null or lower(u.name) like ('%' || lower(:query) || '%')) or " +
            "(:query is null or lower(u.email) like ('%' || lower(:query) || '%'))")
    List<UserEntity> findBySearchQuery(String query);
}
