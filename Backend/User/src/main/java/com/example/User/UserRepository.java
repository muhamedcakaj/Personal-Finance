package com.example.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserNameIgnoreCase(String userName);

    @Query(value = "Select Count(*) as CurrentUserNumber " +
            "from Userr ",
             nativeQuery = true)
    List<Object[]> totalUserNumber();
}
