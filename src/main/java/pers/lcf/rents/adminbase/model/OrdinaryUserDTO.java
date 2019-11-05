package pers.lcf.rents.adminbase.model;

import lombok.Data;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.utils.pageDTO;

import java.util.List;

/**
 * @ClassName UserInfoPage
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/5 9:50
 **/
@Data
public class OrdinaryUserDTO extends pageDTO {
   private List<OrdinaryUser> OrdinaryUsers;
}
