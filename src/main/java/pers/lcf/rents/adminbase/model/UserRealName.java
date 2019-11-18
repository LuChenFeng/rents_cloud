package pers.lcf.rents.adminbase.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRealName {

    private String id;
    private String userInfoId;
    private  String userName;
    private String img1;
    private String img2;
    private Byte hasHandle;
    private String gmtCreate;
    private String gmtModified;
    private  String gmtCreateBegin;
    private String gmtCreateEnd;


}