package com.example.demo.service.impl;

import com.example.demo.bo.Tracelogs;
import com.example.demo.bo.User;
import com.example.demo.dao.IHistoryDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.SearchUserResponse;
import com.example.demo.error.BtcException;
import com.example.demo.service.ITraceLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraceLogService implements ITraceLogService {

    private final IUserDao userDao;
    private final IHistoryDao historyDao;
    @Override
    public List<SearchUserResponse> findUserTraceLog(String username, String email) {
        final User user = checkUserByUsernameAndEmail(username, email);
        final List<Tracelogs> res = historyDao.findTraceLogsByUserId(user.getId());
        final List<SearchUserResponse> resList = new ArrayList<>();
        res.forEach(e->{
            //should i use modelMapper?
            val each = SearchUserResponse.builder()
                    .username(username)
                    .btcPrice(e.getBtc_price())
                    .afterBalance(e.getAfter_balance())
                    .btcAmount(e.getBtc_amount())
                    .build();
            resList.add(each);

        });

        return resList;
    }
    private User checkUserByUsernameAndEmail(String username, String email){
        final Optional<User> userOptional = userDao.findByUsernameAndEmail(username, email);
        if(userOptional.isEmpty()){
            log.trace("使用者不存在");
            System.out.println();
            throw new BtcException("使用者不存在");
        }
        return userOptional.get();
    }
}
