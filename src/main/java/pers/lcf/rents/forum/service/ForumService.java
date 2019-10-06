package pers.lcf.rents.forum.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pers.lcf.rents.forum.model.*;

import java.util.List;

public interface ForumService {
    List<PostsType> selectTypeByIsHave(Byte isHave);
    Integer insertPostInfo(PostsInfo postsInfo);
    PostDetails getPostInfoByid(String id);
    Integer insertPostsReport(PostsReport postsReport);
    Integer insertPostsComment(PostsComment postsComment);
    Integer delPostsCommentById(String id);
    Integer insertPostsReply(PostsReply postsReply);
    Integer delPostsReplyById(String id);
    List<CommentReply> getCommentByPostsId(String id);
}
