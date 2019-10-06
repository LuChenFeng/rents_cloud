package pers.lcf.rents.forum.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PostsInfo {
    private String id;
    private String postTypeId;
    private String userInfoId;
    private String title;
    private BigDecimal price;
    private Integer replyCount;
    private Integer limitSex;
    private Byte isFlag;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String locationName;
    private String locationAddress;
    private String gmtCreate;
    private String gmtModified;
    private String contents;
    private List<PostsImg> postsImgs;
}