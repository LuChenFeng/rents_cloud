package pers.lcf.rents.userbase.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserType {

    private String id;
    private String userTypeName;
    private Date gmtCreate;
    private Date gmtModified;

}