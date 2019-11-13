package pers.lcf.rents.forum.model;

import lombok.Data;

@Data
public class PostsReport {
    private String id;

    private String postsInfoId;

    private String userInfoId;

    private String reportTitle;

    private String reportImg1;

    private String reportImg2;

    private String gmtCreate;

    private String gmtModified;

    private Byte hasHandle;

    private String reportContents;
  }