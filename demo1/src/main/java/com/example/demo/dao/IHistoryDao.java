package com.example.demo.dao;

import com.example.demo.bo.Tracelogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistoryDao extends JpaRepository<Tracelogs, Integer> {
    @Query(value = "SELECT * FROM tracelogs WHERE user_id = :userId", nativeQuery = true)
    public List<Tracelogs> findTraceLogsByUserId(Integer userId);
}
