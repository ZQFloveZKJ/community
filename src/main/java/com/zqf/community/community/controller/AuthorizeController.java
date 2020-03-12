package com.zqf.community.community.controller;

import com.zqf.community.community.dto.AccessTockenDTO;
import com.zqf.community.community.provider.GithubProvider;
import com.zqf.community.community.provider.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state){
        System.out.println("code:=>"+code);
        AccessTockenDTO accessTockenDTO=new AccessTockenDTO();
        accessTockenDTO.setClient_id(client_id);
        accessTockenDTO.setClient_secret(client_secret);
        accessTockenDTO.setCode(code);
        accessTockenDTO.setRedirect_uri(redirect_uri);
        accessTockenDTO.setState(state);

        String accessTocken = githubProvider.getAccessTocken(accessTockenDTO);
        System.out.println("ddd=="+accessTocken);
        GithubUser user = githubProvider.getUser(accessTocken);
        System.out.println(user.getName());
        return "index";
    }
}
