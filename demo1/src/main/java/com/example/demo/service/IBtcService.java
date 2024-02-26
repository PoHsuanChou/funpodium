package com.example.demo.service;

public interface IBtcService {

    public String buyBtcService(String username, String email,Integer btc);

    public String sellBtcService(String username, String email,Integer btc);
}
