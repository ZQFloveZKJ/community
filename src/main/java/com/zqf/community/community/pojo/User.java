package com.zqf.community.community.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmt_greate;
    private Long gmt_modified;
    private String avatar_url;
}
