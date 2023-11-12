package com.resaschool.infrastructure.repository;

import com.resaschool.infrastructure.model.ApplicationEntity;
import com.resaschool.infrastructure.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
