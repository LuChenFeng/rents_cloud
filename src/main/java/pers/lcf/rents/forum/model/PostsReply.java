package pers.lcf.rents.forum.model;

import lombok.Data;

import java.util.Date;
@Data
public class PostsReply {

    private String id;
    private String postsCommentId;
    private String userInfoId;
    private String gmtCreate;
    private String gmtModified;
    private String content;

  }