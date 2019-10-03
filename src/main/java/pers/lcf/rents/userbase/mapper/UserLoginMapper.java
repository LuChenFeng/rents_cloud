package pers.lcf.rents.userbase.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.userbase.model.UserLogin;
import pers.lcf.rents.userbase.model.UserLoginExample;

import java.util.List;
@Mapper
public interface UserLoginMapper {

    long countByExample(UserLoginExample example);

    int deleteByExample(UserLoginExample example);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    List<UserLogin> selectByExample(UserLoginExample example);

    int updateByExampleSelective(@Param("record") UserLogin record, @Param("example") UserLoginExample example);

    int updateByExample(@Param("record") UserLogin record, @Param("example") UserLoginExample example);
}