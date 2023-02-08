package com.andreyb34rus.JM_Task_3_1_1.repository;

import com.andreyb34rus.JM_Task_3_1_1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface
UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u join fetch u.roles where u.username = :username")
    User findByUsername(String username);
}
