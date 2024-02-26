package com.example.demo.dao;

import com.example.demo.bo.Account;
import com.example.demo.bo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountDao extends JpaRepository<Account, Integer> {
    Optional<Account>  findByUserId(Integer userId);
}
