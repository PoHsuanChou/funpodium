package com.example.demo.dao;

import com.example.demo.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserDao extends JpaRepository<User, Integer> {
    public Optional<User> findByUsernameAndEmail(String username, String email);
}
