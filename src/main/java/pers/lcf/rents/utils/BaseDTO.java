package pers.lcf.rents.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/8 10:10
 **/
@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = -8315132785161566453L;
    private  long pageNo=1L;
    private  long pageSize=4L;
}
