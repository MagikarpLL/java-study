package cn.magikarpll.framework.springboot.web;

import cn.magikarpll.framework.springboot.domain.User;
import cn.magikarpll.framework.springboot.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private UserSerivce userSerivce;

    @RequestMapping(value = "/index.html")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest httpServletRequest, LoginCommand loginCommand){
        boolean isValidUser = userSerivce.hasMatchUser(loginCommand.getUserName(), loginCommand.getPassword());
        if(!isValidUser) {
            return new ModelAndView("login", "error", "用户名或密码错误");
        }else {
            User user = userSerivce.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(httpServletRequest.getLocalAddr());
            user.setLastVisit(new Date());
            userSerivce.loginSuccess(user);
            httpServletRequest.getSession().setAttribute("user", user);
            return new ModelAndView("main");
        }
    }

}
