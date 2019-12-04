package pers.lcf.rents.userbase.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogin {
    private String id;
    private String userTypeId;
    private String loginName;
    private String passwordOld;
    private String password;
    private Byte isState;
    private  String tel;
    private String gmtCreate;
    private String gmtModified;
}