package pers.lcf.rents.forum.service;

import pers.lcf.rents.forum.model.*;

import java.util.List;

public interface ForumService {
    List<PostsType> selectTypeByIsHave(Byte isHave);
    Integer insertPostInfo(PostsInfo postsInfo);
    Integer delPostsInfoById(String id);
    PostDetailsPage getPostDetailsByPage(PostDeatailsDTO postDeatailsDTO);
    PostDetails getPostInfoById(String id);
    Integer insertPostsReport(PostsReport postsReport);
    Integer insertPostsComment(PostsComment postsComment);
    Integer delPostsCommentById(String id);
    Integer insertPostsReply(PostsReply postsReply);
    Integer delPostsReplyById(String id);
    List<CurrentComment> getCommentByPostsId(String id);
    List<PostsReply> getReplyByCommentId(String id);


}
