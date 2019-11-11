package pers.lcf.rents.userbase.model;

import lombok.Data;

/**
 * @ClassName UserLoginAppInfo
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/26 16:50
 * 用户登入时传递从APP传入的参数对象
 **/
@Data
public class UserLoginAppInfo {
    private  Boolean loginMethod;
    private String userTypeName;
    private  String password;
    private String loginName;
    private String userName;
    private String avatar;
    private  String tel;
}
