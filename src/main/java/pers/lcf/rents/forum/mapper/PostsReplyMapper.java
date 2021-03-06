package pers.lcf.rents.forum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.lcf.rents.forum.model.PostsReply;
import pers.lcf.rents.forum.model.PostsReplyExample;

import java.util.List;
@Mapper
public interface PostsReplyMapper {
    List<PostsReply> getReplyByCommentId(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    long countByExample(PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int deleteByExample(PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int insert(PostsReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int insertSelective(PostsReply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    List<PostsReply> selectByExampleWithBLOBs(PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    List<PostsReply> selectByExample(PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int updateByExampleSelective(@Param("record") PostsReply record, @Param("example") PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") PostsReply record, @Param("example") PostsReplyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table posts_reply
     *
     * @mbg.generated Sun Oct 06 16:56:56 CST 2019
     */
    int updateByExample(@Param("record") PostsReply record, @Param("example") PostsReplyExample example);
}