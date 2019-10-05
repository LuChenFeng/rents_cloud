package pers.lcf.rents.userbase.model;


import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private String id;
    private String userLoginId;
    private String rentsGroupId;
    private String userName;
    private String avatar;
    private String sex;
    private String birthady;
    private String tel;
    private String statement;
    private Integer reportSum;
    private String gmtCreate;
    private String gmtModified;
}