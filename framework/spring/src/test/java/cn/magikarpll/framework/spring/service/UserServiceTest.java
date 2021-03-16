package cn.magikarpll.framework.spring.service;

import cn.magikarpll.framework.spring.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration("classpath*:/spring-context.xml")
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserSerivce userSerivce;

    @Test
    public void hasMatchUser(){
        boolean b1 = userSerivce.hasMatchUser("admin","123456");
        boolean b2 = userSerivce.hasMatchUser("admin", "1111");
        assert b1;
        assert !b2;
    }

    @Test
    public void findUserByUserName(){
        User user = userSerivce.findUserByUserName("admin");
        assert "admin".equals(user.getUsername());
    }

    @Test
    public void loginSuccess(){
        User user = User.builder().username("admin").userId(1).credits(0).build();
        userSerivce.loginSuccess(user);
        User result = userSerivce.findUserByUserName("admin");
        assert 5 == result.getCredits();
    }

}
