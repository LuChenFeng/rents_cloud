package pers.lcf.rents.userbase.model;

import lombok.Data;

import java.util.Date;

@Data
public class RentsOut {
    private String id;
    private String userInfoId;
    private String date;

    private String outDate;
    private String info;

}