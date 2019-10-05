package pers.lcf.rents.forum.model;

import lombok.Data;

@Data
public class PostsComment {
    private String id;
    private String postsInfoId;
    private String userInfoId;
    private String content;
    private String gmtCreate;
    private String gmtModified;

 }