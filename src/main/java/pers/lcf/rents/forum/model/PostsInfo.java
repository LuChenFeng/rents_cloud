package pers.lcf.rents.forum.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostsInfo {
    private String id;
    private String postTypeId;
    private String userInfoId;
    private String title;
    private BigDecimal price;
    private Integer replycount;
    private Integer expectsex;
    private Byte isFlag;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String locationname;
    private String locationaddress;
    private String contents;
    private String gmtCreate;
    private String gmtModified;
}