package pers.lcf.rents.adminbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.userbase.mapper.UserInfoMapper;
import pers.lcf.rents.userbase.model.UserInfoExample;

import java.util.List;

/**
 * @ClassName AdminBaseServiceImpl
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/5 12:46
 **/
@Service
public class AdminBaseServiceImpl implements AdminBaseService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public OrdinaryUserDTO getOrdinaryUsersByDTO(OrdinaryUserDTO ordinaryUserDTO) {
   List<OrdinaryUser> ordinaryUsers= userInfoMapper.getOrdinaryUsersByDTO(ordinaryUserDTO);
OrdinaryUserDTO dto=new OrdinaryUserDTO();
dto.setOrdinaryUsers(ordinaryUsers);
        return dto;
    }
}
