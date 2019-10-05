package pers.lcf.rents.forum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.forum.model.PostsType;
import pers.lcf.rents.forum.model.PostsTypeExample;

import java.util.List;
@Mapper
public interface PostsTypeMapper {

    long countByExample(PostsTypeExample example);

    int deleteByExample(PostsTypeExample example);

    int insert(PostsType record);

    int insertSelective(PostsType record);

    List<PostsType> selectByExample(PostsTypeExample example);

    int updateByExampleSelective(@Param("record") PostsType record, @Param("example") PostsTypeExample example);

    int updateByExample(@Param("record") PostsType record, @Param("example") PostsTypeExample example);
}