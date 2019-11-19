package pers.lcf.rents.forum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.adminbase.model.PostsInfoDetails;
import pers.lcf.rents.forum.model.PostDeatailsDTO;
import pers.lcf.rents.forum.model.PostDetails;
import pers.lcf.rents.forum.model.PostsInfo;
import pers.lcf.rents.forum.model.PostsInfoExample;

import java.util.List;

@Mapper
public interface PostsInfoMapper {
    List<List<?>> getPostsInfoByDTO(@Param("record") PostsInfoDetails postsInfoDetails, @Param("start") Integer start, @Param("end") Integer end);
    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.forum.model.PostDetails
     * @Author: lcf
     * @Date: 2019/10/9 0:56
     * 查询帖子详情
     */
    PostDetails getPostInfoById(String id);

    List<PostDetails> getPostDetailsByPage(PostDeatailsDTO postDeatailsDTO);

    long countByExample(PostsInfoExample example);

    int deleteByExample(String id);

    int insert(PostsInfo record);

    int insertSelective(PostsInfo record);

    List<PostsInfo> selectByExampleWithBLOBs(PostsInfoExample example);

    List<PostsInfo> selectByExample(PostsInfoExample example);

    int updateByExampleSelective(@Param("record") PostsInfo record, @Param("example") PostsInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsInfo record, @Param("example") PostsInfoExample example);

    int updateByExample(@Param("record") PostsInfo record, @Param("example") PostsInfoExample example);

}