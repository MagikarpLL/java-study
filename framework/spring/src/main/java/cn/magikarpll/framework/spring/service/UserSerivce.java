package cn.magikarpll.framework.spring.service;

import cn.magikarpll.framework.spring.dao.LoginLogDao;
import cn.magikarpll.framework.spring.dao.UserDao;
import cn.magikarpll.framework.spring.domain.LoginLog;
import cn.magikarpll.framework.spring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSerivce {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginLogDao loginLogDao;

    public boolean hasMatchUser(String username, String password){
        int matchCount = userDao.getMatchCount(username, password);
        return matchCount>0;
    }

    public User findUserByUserName(String username){
        return userDao.findUserByUserName(username);
    }

    @Transactional
    public void loginSuccess(User user){
        user.setCredits(user.getCredits() + 5);
        LoginLog loginLog = LoginLog.builder().userId(user.getUserId()).ip(user.getLastIp()).loginDate(user.getLastVisit()).build();
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLogSql(loginLog);
    }

}
