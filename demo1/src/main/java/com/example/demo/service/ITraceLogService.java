package com.example.demo.service;

import com.example.demo.bo.Tracelogs;
import com.example.demo.dto.SearchUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITraceLogService {
    public List<SearchUserResponse> findUserTraceLog(String username, String email);

}
