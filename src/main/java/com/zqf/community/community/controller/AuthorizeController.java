package com.zqf.community.community.controller;

import com.zqf.community.community.dto.AccessTockenDTO;
import com.zqf.community.community.mapper.UserMapper;
import com.zqf.community.community.pojo.User;
import com.zqf.community.community.provider.GithubProvider;
import com.zqf.community.community.provider.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.client.uri}")
    private String redirect_uri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpSession session, HttpServletResponse response){
        System.out.println("code:=>"+code);
        AccessTockenDTO accessTockenDTO=new AccessTockenDTO();
        accessTockenDTO.setClient_id(client_id);
        accessTockenDTO.setClient_secret(client_secret);
        accessTockenDTO.setCode(code);
        accessTockenDTO.setRedirect_uri(redirect_uri);
        accessTockenDTO.setState(state);

        String accessTocken = githubProvider.getAccessTocken(accessTockenDTO);
        GithubUser user = githubProvider.getUser(accessTocken);
        if (user!=null&&user.getId()!=null){//登录成功
            User user1=new User();
            String token=UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setAvatar_url(user.getAvatar_url());
            user1.setName(user.getName());
            user1.setAccount_id(String.valueOf(user.getId()));
            user1.setGmt_greate(System.currentTimeMillis());
            user1.setGmt_modified(user1.getGmt_greate());
            userMapper.insert(user1);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {//登录失败
            return "redirect:/";
        }
    }
}
