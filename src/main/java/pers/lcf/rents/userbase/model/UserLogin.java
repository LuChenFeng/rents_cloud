package pers.lcf.rents.userbase.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogin {
    private String id;
    private String userTypeId;
    private String loginName;
    private String password;
    private Byte isState;
    private Date gmtCreate;
    private Date gmtModified;
}