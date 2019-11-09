package pers.lcf.rents.userbase.mapper;

import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserInfoExample;

import java.util.Collection;
import java.util.List;


public interface UserInfoMapper {
    List<List<?>> getOrdinaryUsersByDTO(@Param("record") OrdinaryUser ordinaryUser, @Param("start") Integer start, @Param("end") Integer end);

    Integer delOrdinaryUserById(@Param("list") List<String> ids);

    UserInfo getUserInfoByLoginId(String loginName);

    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);
}