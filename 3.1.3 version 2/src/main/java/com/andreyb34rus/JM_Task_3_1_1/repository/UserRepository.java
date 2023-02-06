package com.andreyb34rus.JM_Task_3_1_1.repository;

import com.andreyb34rus.JM_Task_3_1_1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
