package pers.lcf.rents.adminbase.model;

import lombok.Data;
import pers.lcf.rents.utils.pageDTO;

import java.util.List;

/**
 * @ClassName UserRealNameDTO
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/16 15:55
 **/
@Data
public class UserRealNameDTO extends pageDTO {
    private List<UserRealName> userRealNames;
}
