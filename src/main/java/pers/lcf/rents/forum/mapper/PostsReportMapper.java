package pers.lcf.rents.forum.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pers.lcf.rents.adminbase.model.Report;
import pers.lcf.rents.adminbase.model.User;
import pers.lcf.rents.forum.model.PostsReport;
import pers.lcf.rents.forum.model.PostsReportExample;

@Mapper
public interface PostsReportMapper {
    List<List<?>> getPostsReportsByDTO(@Param("record") Report Report, @Param("start") Integer start, @Param("end") Integer end);

    Integer delPostsReportById(@Param("list") List<String> ids);

    long countByExample(PostsReportExample example);

    int deleteByExample(PostsReportExample example);

    int insert(PostsReport record);

    int insertSelective(PostsReport record);

    List<PostsReport> selectByExampleWithBLOBs(PostsReportExample example);

    List<PostsReport> selectByExample(PostsReportExample example);

    int updateByExampleSelective(@Param("record") PostsReport record, @Param("example") PostsReportExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsReport record, @Param("example") PostsReportExample example);

    int updateByExample(@Param("record") PostsReport record, @Param("example") PostsReportExample example);
}