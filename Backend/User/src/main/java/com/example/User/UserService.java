package com.example.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public UserEntity findById(int id);
    public UserEntity save(UserEntity userEntity);

    public List<UserEntity> findAll();

    public void deleteById(int id);

    public void deleteCacheEvict(String userName);

    public UserEntity findByUserNameIgnoreCase(String userName);

    boolean checkPassword(String userName, String password);

    public String updateBilancColumns(String username, Map<String, String> columnUpdates);

    public List<Object[]> totalUserNumber();
}
