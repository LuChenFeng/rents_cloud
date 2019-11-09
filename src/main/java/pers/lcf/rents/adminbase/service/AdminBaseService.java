package pers.lcf.rents.adminbase.service;

import org.springframework.web.bind.annotation.RequestBody;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.model.OrdinaryUsersPei;

import java.util.List;

public interface AdminBaseService {
    OrdinaryUserDTO getOrdinaryUsersByDTO(OrdinaryUserDTO userInfoPage);

    Integer updateOrdinaryUser(OrdinaryUser ordinaryUser);

    Integer delOrdinaryUserById(List<String> ids);

    OrdinaryUsersPei getOrdinaryUsersPei();
}
