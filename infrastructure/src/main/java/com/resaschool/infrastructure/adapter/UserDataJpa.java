package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.SchoolEntity;
import com.resaschool.infrastructure.model.UserEntity;
import com.resaschool.infrastructure.repository.UserRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.UserData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserDataJpa implements UserData {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(UserEntity::toModel);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserEntity::toModel)
                .collect(Collectors.toList());
    }
}
