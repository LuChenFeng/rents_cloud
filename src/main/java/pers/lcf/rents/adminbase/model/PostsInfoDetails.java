package pers.lcf.rents.adminbase.model;

import lombok.Data;
import pers.lcf.rents.forum.model.PostsInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName PostsInfoDetails
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/19 13:20
 **/
@Data
public class PostsInfoDetails {

    private String userId;
    private String id;
    private String postTypeName;
    private String userName;
    private Integer reportSum;
    private String title;
    private String contents;
    private BigDecimal price;
    private String gmtCreate;
    private  String gmtCreateBegin;
    private String gmtCreateEnd;

}
