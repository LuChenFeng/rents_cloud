package pers.lcf.rents.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName pageDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/8 10:10
 **/
@Data
public class pageDTO implements Serializable {
    private static final long serialVersionUID = -8315132785161566453L;
    private  Integer pageNo=1;
    private  Integer pageSize=4;
    private  Integer totalCount;
    private  Integer totalPage;
    private Integer start;
    private  Integer end;
}
