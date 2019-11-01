package pers.lcf.rents.forum.model;

import lombok.Data;
import pers.lcf.rents.utils.pageDTO;

/**
 * @ClassName PostDeatailsDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/8 10:14
 **/
@Data
public class PostDeatailsDTO extends pageDTO {
    private  String userInfoId;
    private String likeStr;
    private  String city;
    private  Integer  start;
    private  Integer end;
    private  Integer pageNum;
    private  Byte isHave;

}
