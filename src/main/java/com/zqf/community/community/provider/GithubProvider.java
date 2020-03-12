package com.zqf.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.zqf.community.community.dto.AccessTockenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GithubProvider {
    public String getAccessTocken(AccessTockenDTO accessTockenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        System.out.println("accs:"+accessTockenDTO);
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTockenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string= response.body().string();
                System.out.println("string========"+string);
                String token = string.split("&")[0].split("=")[1];
                System.out.println("token=="+token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String token1){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("https://api.github.com/user?access_token="+token1).build();
        try {
            Response response=client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
         return null;

    }
}
