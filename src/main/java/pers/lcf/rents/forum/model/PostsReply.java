package pers.lcf.rents.forum.model;

import lombok.Data;

@Data
public class PostsReply {
    private String id;
    private String postsOmmentId;
    private String userInfoId;
    private String content;
    private String gmtCreate;
    private String gmtModified;
}