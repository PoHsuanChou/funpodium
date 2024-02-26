package com.example.demo.service.impl;

import com.example.demo.BTCPriceSimulation;
import com.example.demo.bo.Account;
import com.example.demo.bo.Tracelogs;
import com.example.demo.bo.User;
import com.example.demo.dao.IAccountDao;
import com.example.demo.dao.IHistoryDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.error.BtcException;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSerice implements IUserService {

    private final IUserDao userDao;

    private final IAccountDao accountDao;

    private final IHistoryDao historyDao;

    private final BTCPriceSimulation btcPriceSimulation;

    @Transactional
    @Override
    public void createUser(String username, String email){
        final Optional<User> userOptional = userDao.findByUsernameAndEmail(username, email);
        userOptional.ifPresent(user -> {
            log.trace("使用者已經存在");
            throw new BtcException("使用者已經存在");

        });
        final User createUser = User.builder().email(email).username(username).build();
        final User savedUser;
        try{
            savedUser = userDao.save(createUser);
            createAccount(savedUser.getId());
        }catch (Exception e){
            log.trace("無法存取使用者");
            throw new BtcException("無法存取使用者");
        }
        final int btcPrice = btcPriceSimulation.currentBTC.get();
        createlog(savedUser,btcPrice,1000,0);
    }

    @Override
    @Transactional
    public void deleteUser(String username, String email) {
        final Optional<User> userOptional = userDao.findByUsernameAndEmail(username, email);
        if(!userOptional.isPresent()){
            log.trace("使用者不存在");
            throw new BtcException("使用者不存在");
        }
        User user = userOptional.get();
        try {
            accountDao.deleteById(user.getId());
            userDao.delete(user);

        } catch (Exception e) {
            log.trace("無法刪除使用者");
            throw new BtcException("無法刪除使用者");
        }
    }

    private void createAccount(int userId) {
        Account account = Account.builder()
                .balance(1000)
                .btc_amount(0)
                .userId(userId)
                .build();
        try {
            // 在這裡執行新增 Account 的操作
            accountDao.save(account);
        } catch (Exception e) {
            log.trace("無法成功新增account balance");
            throw new BtcException("無法成功新增account balance");
        }

    }
    private void createlog(User savedUser,Integer btcPrice, Integer afterBalance, Integer btcAmount){
        Tracelogs createHistory = Tracelogs.builder()
                .user_id(savedUser.getId())
                .after_balance(afterBalance)
                .btc_price(btcPrice)
                .btc_amount(btcAmount)
                .build();
        try{
            historyDao.save(createHistory);
        }catch (Exception e){
            log.trace("無法存歷史資料");
            throw new BtcException("無法存歷史資料");
        }
    }

}
