package com.example.demo.service.impl;

import com.example.demo.BTCPriceSimulation;
import com.example.demo.bo.Account;
import com.example.demo.bo.Tracelogs;
import com.example.demo.bo.User;
import com.example.demo.dao.IAccountDao;
import com.example.demo.dao.IHistoryDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.error.BtcException;
import com.example.demo.service.IBtcService;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class BtcService implements IBtcService {

    private final IAccountDao accountDao;
    private final IUserDao userDao;
    private final BTCPriceSimulation btcPriceSimulation;
    private final IHistoryDao historyDao;
    @Override
    @Transactional
    public String buyBtcService(String username, String email,Integer btc) {
        final User user = checkUserByUsernameAndEmail(username,email,btc);
        final Account account = checkUserAccountByUserId(user);

        final int curBtc = btcPriceSimulation.currentBTC.get();
        final int totalPriceForBuyingBtc = curBtc * btc;
        final int userBalance = account.getBalance();
        final int currBtcAmount = account.getBtc_amount();
        final int afterBuyBTCAccount = userBalance - totalPriceForBuyingBtc;
        final int afterBuyBTC = currBtcAmount + btc;

        if(afterBuyBTCAccount < 0){
            return "存款不足";
        }

        account.setBtc_amount(afterBuyBTC);
        account.setBalance(afterBuyBTCAccount);

        try{
            accountDao.save(account);
        }catch (Exception e){
            log.trace("無法更新存款帳號");
            throw new BtcException("無法更新存款帳號");
        }
        createlog(user,curBtc,afterBuyBTCAccount,afterBuyBTC);
        return String.format("使用者: %s 購買BTC再價格: %d 的時候買入，使用者餘額為：%d",username,curBtc,afterBuyBTCAccount);

    }

    @Override
    public String sellBtcService(String username, String email, Integer btc) {
        final User user = checkUserByUsernameAndEmail(username,email,btc);
        final Account account = checkUserAccountByUserId(user);

        final int curBtc = btcPriceSimulation.currentBTC.get();
        final int totalPriceForSellingBtc = curBtc * btc;
        final int userBalance = account.getBalance();
        final int currBtcAmount = account.getBtc_amount();
        final int afterSellBTCAccount = userBalance + totalPriceForSellingBtc;
        final int afterSellBTC = currBtcAmount - btc;

        if(afterSellBTC<0){
            return "BTC不足";
        }

        account.setBtc_amount(afterSellBTC);
        account.setBalance(afterSellBTCAccount);

        try{
            accountDao.save(account);
        }catch (Exception e){
            log.trace("無法更新存款帳號");
            throw new BtcException("無法更新存款帳號");
        }
        createlog(user,curBtc,afterSellBTCAccount,afterSellBTC);
        return String.format("使用者: %s 賣出BTC再價格: %d 的時候賣出，使用者餘額為：%d",username,curBtc,afterSellBTC);

    }


    private User checkUserByUsernameAndEmail(String username, String email,Integer btc){
        final Optional<User> userOptional = userDao.findByUsernameAndEmail(username, email);
        if(userOptional.isEmpty()){
            log.trace("使用者不存在");
            throw new BtcException("使用者不存在");
        }
        return userOptional.get();
    }
    private Account checkUserAccountByUserId(User user){
        final Optional<Account> userAccount = accountDao.findByUserId(user.getId());
        if(userAccount.isEmpty()){
            log.trace("帳號不存在");
            throw new BtcException("帳號不存在");
        }
        return userAccount.get();
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
