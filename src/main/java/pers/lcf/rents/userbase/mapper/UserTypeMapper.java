package pers.lcf.rents.userbase.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.userbase.model.UserType;
import pers.lcf.rents.userbase.model.UserTypeExample;

import java.util.List;

@Mapper
public interface UserTypeMapper {

    long countByExample(UserTypeExample example);

    int deleteByExample(UserTypeExample example);

    int insert(UserType record);

    int insertSelective(UserType record);

    List<UserType> selectByExample(UserTypeExample example);

    int updateByExampleSelective(@Param("record") UserType record, @Param("example") UserTypeExample example);

    int updateByExample(@Param("record") UserType record, @Param("example") UserTypeExample example);
}