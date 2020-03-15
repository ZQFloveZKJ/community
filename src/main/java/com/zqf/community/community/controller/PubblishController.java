package com.zqf.community.community.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.zqf.community.community.mapper.QuestionMapper;
import com.zqf.community.community.mapper.UserMapper;
import com.zqf.community.community.pojo.Question;
import com.zqf.community.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PubblishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("pubblish")
    public String pubblish() {
        return "publish";
    }

    @PostMapping("pubblish")
    public String dopubblish(@RequestParam("title") String title, @RequestParam("description") String description, Model model, @RequestParam("tag") String tag, HttpServletRequest request) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null|| title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null || description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user=null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
        }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        System.out.println(question.toString());
        questionMapper.create(question);
        return "redirect:/";
    }
}
