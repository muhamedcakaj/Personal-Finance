package com.example.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserEntity findById(int id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @CachePut(value = "users", key = "#userEntity.userName")
    @Override
    public UserEntity save(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Username already exists. Please try another one.");
        }
    }

    @CacheEvict(value = "users", key = "#userName")
    @CachePut(value = "users", key = "#userName")
    @Override
    public String updateBilancColumns(String userName, Map<String, String> columnUpdates) {
        UserEntity user = userRepository.findByUserNameIgnoreCase(userName);

        for (Map.Entry<String, String> entry : columnUpdates.entrySet()) {
            String columnName = entry.getKey();
            String columnValue = entry.getValue();

            // Update the specified column
            switch (columnName) {
                case "name":
                    user.setName(columnValue);
                    break;
                case "surname":
                    user.setSurname(columnValue);
                    break;
                case "userName":
                    user.setUserName(columnValue);
                    break;
                case "password":
                    user.setPassword(columnValue);
                    break;
                case "email":
                    user.setEmail(columnValue);
                    break;
                case "role":
                    user.setRole(columnValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid column name: " + columnName);
            }
        }
        UserEntity updatedBilanc = userRepository.save(user);
        return updatedBilanc.toString();
    }
    @Cacheable(value = "users", key = "#userName")
    @Override
    public UserEntity findByUserNameIgnoreCase(String userName) {
        UserEntity user = userRepository.findByUserNameIgnoreCase(userName);
        if (user == null) {
            // Don't cache the non-existing username
            return null;
        }
        return user;
    }
    @Override
    public boolean checkPassword(String userName, String password) {
        UserEntity user = userRepository.findByUserNameIgnoreCase(userName);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    //Deleting the Chacable username method
    @CacheEvict(value = "users", key = "#userName")
    public void deleteCacheEvict(String userName){

    }
    //ADMIN METHODS
    @CacheEvict(value = "users", key = "#id")
    @Override
    public void deleteById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<Object[]> totalUserNumber(){
        return this.userRepository.totalUserNumber();
    }

}
