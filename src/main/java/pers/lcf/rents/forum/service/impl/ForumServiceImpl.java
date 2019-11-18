package pers.lcf.rents.forum.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.forum.mapper.*;
import pers.lcf.rents.forum.model.*;
import pers.lcf.rents.forum.service.ForumService;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName ForumServiceImpl
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/5 21:25
 **/
@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private PostsTypeMapper postsTypeMapper;
    @Autowired
    private PostsInfoMapper postsInfoMapper;
    @Autowired
    private PostsImgMapper postsImgMapper;

    @Autowired
    private PostsReportMapper postsReportMapper;

    @Autowired
    private PostsCommentMapper postsCommentMapper;

    @Autowired
    private PostsReplyMapper postsReplyMapper;

    /**
     * @Param: [isHave]
     * @Return: java.util.List<pers.lcf.rents.forum.model.PostsType>
     * @Author: lcf
     * @Date: 2019/10/5 22:54
     * 查帖子类别
     */
    @Override
    public List<PostsType> selectTypeByIsHave(Byte isHave) {
        PostsTypeExample example = new PostsTypeExample();
        PostsTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsHaveEqualTo(isHave);
        List<PostsType> postsTypes = postsTypeMapper.selectByExample(example);
        return postsTypes;
    }

    /**
     * @Param: [postsInfo]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 11:36
     * 发布帖子
     */
    @Override
    public Integer insertPostInfo(PostsInfo postsInfo) {
        String infoId = IdUtil.simpleUUID();//hutool生成id
        byte isFlag = 0;
        postsInfo.setId(infoId);
        postsInfo.setIsFlag(isFlag);
        postsInfo.setReplyCount(0);
        postsInfo.setGmtCreate(DateUtil.now());
        postsInfo.setGmtModified(DateUtil.now());
        int infoFlag = postsInfoMapper.insert(postsInfo);
        if ( postsInfo.getPostsImgs().size()<=0) {
            return infoFlag;
        }
        //用户有上传图片继续
        List<PostsImg> postsImgs = CollUtil.newArrayList();
        Iterator<PostsImg> itImg = (postsInfo.getPostsImgs()).iterator();
        while (itImg.hasNext()) {
            PostsImg postsImg = itImg.next();
            postsImg.setId(IdUtil.simpleUUID());
            postsImg.setPostsInfoId(infoId);
            postsImg.setGmtCreate(DateUtil.now());
            postsImg.setGmtModified(DateUtil.now());
            postsImgs.add(postsImg);
        }
        int imgFlag = postsImgMapper.insert(postsImgs);
        return infoFlag;
    }

    /**
     * @Param: [id]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/7 19:52
     * 联级删除帖子
     */
    @Override
    public Integer delPostsInfoById(String id) {
        int flag = postsInfoMapper.deleteByExample(id);
        return flag;
    }

    /**
     * @Param: [postDeatailsDTO]
     * @Return: java.util.List<pers.lcf.rents.forum.model.PostDetails>
     * @Author: lcf
     * @Date: 2019/10/8 10:19
     * 分页查询帖子简要信息
     */
    @Override
    public PostDetailsPage getPostDetailsByPage(PostDeatailsDTO postDeatailsDTO) {
        int[] startEnd = PageUtil.transToStartEnd(postDeatailsDTO.getPageNo(), postDeatailsDTO.getPageSize());
        postDeatailsDTO.setStart(startEnd[0]);
        postDeatailsDTO.setEnd(startEnd[1]);
        List<PostDetails> postDetails = postsInfoMapper.getPostDetailsByPage(postDeatailsDTO);
        //取到帖子id
        List<String> postsIds = CollUtil.newArrayList();
        Iterator<PostDetails> itGetId = postDetails.iterator();
        while (itGetId.hasNext()) {
            postsIds.add(itGetId.next().getPostsId());
        }
        if(CollUtil.isEmpty(postDetails)){
            return null;
        }
        //查找帖子附图
       List<PostsImg> postsImgs= postsImgMapper.getImgsByPostsIds(postsIds);
        Iterator<PostDetails> itPostDetails=postDetails.iterator();
        while (itPostDetails.hasNext()){
            PostDetails postDetail=itPostDetails.next();
            Iterator<PostsImg> itPostImg=postsImgs.iterator();
            List<String> imgs=CollUtil.newArrayList();
            int flag=0;
            while (itPostImg.hasNext()){
                PostsImg postsImg=itPostImg.next();
                if(postsImg.getPostsInfoId().equals(postDetail.getPostsId())){
                  imgs.add(postsImg.getImg());
                    ++flag;
                  if(flag>3) {
                    break;
                  }
                }
            }
            if(flag!=0){
                postDetail.setImgs(imgs);
            }
        }
        PostDetailsPage postDetailsPage=new PostDetailsPage();
        postDetailsPage.setPostDetails(postDetails);

        PostsInfoExample example=new PostsInfoExample();
        PostsInfoExample.Criteria criteria=example.createCriteria();
//        统计页数
        if( postDeatailsDTO.getCity()!=null){
            criteria.andLocationAddressLike('%'+postDeatailsDTO.getCity()+'%');
        }
        if(postDeatailsDTO.getUserInfoId()!=null){
            criteria.andUserInfoIdEqualTo(postDeatailsDTO.getUserInfoId());
        }
        long recordTotal= postsInfoMapper.countByExample(example);
        postDetailsPage.setPageTotal((long) PageUtil.totalPage((int)recordTotal,postDeatailsDTO.getPageSize()));
        return postDetailsPage;
    }

    @Override
    public PostDetails getPostInfoById(String id) {
        PostDetails postDetails = postsInfoMapper.getPostInfoById(id);
        if(postDetails==null){
            return null;
        }
//        找该贴附图
        PostsImgExample example = new PostsImgExample();
        PostsImgExample.Criteria criteria = example.createCriteria();
        criteria.andPostsInfoIdEqualTo(id);
        List<PostsImg> postsImgs = postsImgMapper.selectByExample(example);

        List<String> imgs = CollUtil.newArrayList();
        Iterator<PostsImg> itImg = postsImgs.iterator();
        while (itImg.hasNext()) {
            imgs.add(itImg.next().getImg());
        }
        postDetails.setImgs(imgs);
        return postDetails;
    }

    /**
     * @Param: [postsReport]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 16:35
     * 添加帖子举报记录
     */
    @Override
    public Integer insertPostsReport(PostsReport postsReport) {
        postsReport.setId(IdUtil.simpleUUID());
        postsReport.setGmtCreate(DateUtil.now());
        postsReport.setGmtModified(DateUtil.now());
        int flag = postsReportMapper.insertSelective(postsReport);
        return flag;
    }

    /**
     * @Param: [postsComment]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 19:11
     * 添加帖子评论
     */
    @Override
    public Integer insertPostsComment(PostsComment postsComment) {
        postsComment.setId(IdUtil.simpleUUID());
        postsComment.setGmtCreate(DateUtil.now());
        postsComment.setGmtModified(DateUtil.now());
        int flag = postsCommentMapper.insertSelective(postsComment);
        return flag;
    }

    /**
     * @Param: [id]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 19:11
     * 根据id 删除评论
     */
    @Override
    public Integer delPostsCommentById(String id) {
        int flag = postsCommentMapper.deleteByExample(id);
        return flag;
    }

    /**
     * @Param: [postsReply]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 19:14
     * 添加评论回复
     */
    @Override
    public Integer insertPostsReply(PostsReply postsReply) {
        postsReply.setId(IdUtil.simpleUUID());
        postsReply.setGmtCreate(DateUtil.now());
        postsReply.setGmtModified(DateUtil.now());
        int flag = postsReplyMapper.insertSelective(postsReply);
        return flag;
    }

    /**
     * @Param: [id]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/6 19:14
     * 删除评论回复
     */
    @Override
    public Integer delPostsReplyById(String id) {
        PostsReplyExample example = new PostsReplyExample();
        PostsReplyExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        int flag = postsReplyMapper.deleteByExample(example);
        return flag;
    }

    @Override
    public List<CurrentComment> getCommentByPostsId(String id) {
        List<CurrentComment> commentReplies = postsCommentMapper.getCommentByPostsId(id);
        return commentReplies;
    }

    @Override
    public List<PostsReply> getReplyByCommentId(String id) {
        List<PostsReply> postsReplies = postsReplyMapper.getReplyByCommentId(id);
        return postsReplies;
    }

}
