package pers.lcf.rents.forum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.forum.model.PostsImg;
import pers.lcf.rents.forum.model.PostsImgExample;

import java.util.List;

@Mapper
public interface PostsImgMapper {

    long countByExample(PostsImgExample example);

    List<PostsImg> getImgsByPostsIds(@Param("record") List<String>  record);

    int deleteByExample(PostsImgExample example);

    int insert(@Param("record") List<PostsImg>  record);

    int insertSelective(PostsImg record);

    List<PostsImg> selectByExample(PostsImgExample example);

    int updateByExampleSelective(@Param("record") PostsImg record, @Param("example") PostsImgExample example);

    int updateByExample(@Param("record") PostsImg record, @Param("example") PostsImgExample example);
}