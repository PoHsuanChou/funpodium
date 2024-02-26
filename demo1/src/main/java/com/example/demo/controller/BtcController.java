package com.example.demo.controller;

import com.example.demo.BTCPriceSimulation;
import com.example.demo.bo.Tracelogs;
import com.example.demo.dto.*;
import com.example.demo.error.BtcException;
import com.example.demo.service.IBtcService;
import com.example.demo.service.ITraceLogService;
import com.example.demo.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/btc")
@RequiredArgsConstructor

public class BtcController {

    private final BTCPriceSimulation btcPriceSimulation;
    private final IUserService userService;
    private final IBtcService btcService;
    private final ITraceLogService traceLogService;
//    private

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody CreateUserRequest user, BindingResult res){
        if(res.hasErrors()){
            throw new BtcException("something with with email or username");
        }
        final String username = user.getUsername();
        final String email = user.getEmail();
        userService.createUser(username,email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("成功新增帳號");


    }


    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody DeleteUserRequest user,BindingResult res){
        if(res.hasErrors()){
            throw new BtcException("something with with email or username");
        }
        final String username = user.getUsername();
        final String email = user.getEmail();
        userService.deleteUser(username,email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("成功刪除帳號");

    }

    @PostMapping("/buyBtc")
    public ResponseEntity<String> buyBtc(@Valid @RequestBody BuyBtcRequest btcRequest, BindingResult res){
        if(res.hasErrors()){
            throw new BtcException("something with with request");
        }
        final String username = btcRequest.getUsername();
        final String email = btcRequest.getEmail();
        final Integer btc = btcRequest.getBtc();
        String result = btcService.buyBtcService(username,email,btc);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }


    @PostMapping("/sellBtc")
    public ResponseEntity<String> sellBtc(@Valid @RequestBody SellBtcRequest btcRequest, BindingResult res){
        if(res.hasErrors()){
            throw new BtcException("something with with request");
        }
        final String username = btcRequest.getUsername();
        final String email = btcRequest.getEmail();
        final Integer btc = btcRequest.getBtc();
        String result = btcService.sellBtcService(username,email,btc);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }


    @PostMapping("/seachUser")
    public ResponseEntity<List<SearchUserResponse>> searchUserData(@Valid @RequestBody SearchUserRequest searchRequest, BindingResult res){
        if(res.hasErrors()){
            throw new BtcException("something with with request");
        }
        final String username = searchRequest.getUsername();
        final String email = searchRequest.getEmail();
        List<SearchUserResponse> result = traceLogService.findUserTraceLog(username,email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }








}


