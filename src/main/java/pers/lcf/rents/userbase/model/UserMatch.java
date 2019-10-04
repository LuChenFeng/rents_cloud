package pers.lcf.rents.userbase.model;

import lombok.Data;

/**
 * @ClassName UserMatch
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/4 17:26
 * 匹配对象
 **/
@Data
public class UserMatch {
    private String userName;
    private String avatar;
    private String sex;
    private String statement;
    private Integer similarity;
    private UserStyle userStyle;
}
