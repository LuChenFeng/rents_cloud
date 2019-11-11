package pers.lcf.rents.adminbase.service;

import pers.lcf.rents.adminbase.model.User;
import pers.lcf.rents.adminbase.model.UserDTO;
import pers.lcf.rents.adminbase.model.OrdinaryUsersPei;

import java.util.List;

public interface AdminBaseService {
    UserDTO getOrdinaryUsersByDTO(UserDTO userInfoPage);

    UserDTO getAdminUsersDTO(UserDTO userInfoPage);

    Integer updateOrdinaryUser(User user);

    Integer delOrdinaryUserById(List<String> ids);

    OrdinaryUsersPei getOrdinaryUsersPei();

}
