package com.zqf.community.community.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.zqf.community.community.mapper.UserMapper;
import com.zqf.community.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                String token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
            }
        }

        return "index";
    }
}
