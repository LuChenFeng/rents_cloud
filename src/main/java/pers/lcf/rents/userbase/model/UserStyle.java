package pers.lcf.rents.userbase.model;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.util.Date;

@Data
public class UserStyle {

    private String id;

    private String userInfoId;

    private String city;

    private Integer thought;

    private Integer spend;

    private Integer nature;

    private Integer discipline;

    private Integer thinking;

    private Byte isPets;

    private Byte isConstellation;

    private Byte isSports;

    private Byte isChess;

    private Byte isTour;

    private Byte isMusical;

    private Byte isDance;

    private Byte isFilm;

    private Byte isRead;

    private String gmtCreate;

    private String gmtModified;

}