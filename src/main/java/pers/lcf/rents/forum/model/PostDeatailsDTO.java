package pers.lcf.rents.forum.model;

import lombok.Data;
import pers.lcf.rents.utils.BaseDTO;

/**
 * @ClassName PostDeatailsDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/8 10:14
 **/
@Data
public class PostDeatailsDTO {
    private String likeStr;//标题
    private  long pageNo=1L;
    private  long pageSize=4L;
}
