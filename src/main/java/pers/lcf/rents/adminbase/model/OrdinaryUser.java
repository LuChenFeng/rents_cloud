package pers.lcf.rents.adminbase.model;

import lombok.Data;

import java.util.Date;
/**
 * @Param: 
 * @Return: 
 * @Author: lcf
 * @Date: 2019/11/5 22:57
 */
@Data
public class OrdinaryUser {
   
    private String id;
    private String loginName;
    private String userName;
    private String avatar;
    private String sex;
    private Byte hasRealName;
    private String birthady;
    private String tel;
    private Byte isState;
    private Integer reportSum;
    private Integer postsSum;
    private String gmtCreate;
    private  String gmtCreateBegin;
    private String gmtCreateEnd;

}