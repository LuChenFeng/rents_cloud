package pers.lcf.rents.forum.controller;


import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import pers.lcf.rents.forum.model.*;
import pers.lcf.rents.forum.service.ForumService;
import pers.lcf.rents.utils.FileUtil;
import pers.lcf.rents.utils.ResponseJson;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;


@RestController
@EnableAutoConfiguration
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ResponseJson responseJson;

    @Autowired
    private ForumService forumServiceImpl;

    /**
     * @Param: [request]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/5 20:21
     * 上传帖子图片 多图片上传
     */
    @RequestMapping(value = "/uploadimgs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson multipleFilesUpload(HttpServletRequest request) {

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> imageUrl = CollUtil.newArrayList();
        String fileSon = "postsImage/";
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();

            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                String s = FileUtil.uploadFile(entry.getValue(), fileSon);
                imageUrl.add(s);
            }
        }
        responseJson.setSuccessResPonse(imageUrl);
        return responseJson;
    }

    /**
     * @Param: [isHave]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/5 22:53
     * 查帖子类别
     */
    @GetMapping("/selectTypeByIsHave")
    public ResponseJson selectTypeByIsHave(Byte isHave) {
        List<PostsType> postsTypes = forumServiceImpl.selectTypeByIsHave(isHave);
        responseJson.setSuccessResPonse(postsTypes);
        return responseJson;
    }

    /**
     * @Param: [postsInfo]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 14:20
     * 发布帖子
     */
    @PostMapping("/postInfo")
    public ResponseJson insertPostInfo(@RequestBody PostsInfo postsInfo) {
        if (postsInfo.getPostTypeId() == null || postsInfo.getPostTypeId() == "") {
            responseJson.setResPonseSelfMsg("未选择帖子类型");
            return responseJson;
        }
        if (postsInfo.getUserInfoId() == null || postsInfo.getUserInfoId() == "") {
            responseJson.setResPonseSelfMsg("未指定发帖人");
            return responseJson;
        }
        int flag = forumServiceImpl.insertPostInfo(postsInfo);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/7 19:53
     * 联级删除帖子
     */
    @DeleteMapping("/postInfo")
    public ResponseJson delPostsInfoById(String id) {
        int flag = forumServiceImpl.delPostsInfoById(id);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 14:33
     * 根据帖子id 查看帖子详情
     */
    @GetMapping("/getPostInfoByid")
    public ResponseJson getPostInfoByid(String id) {
        PostDetails postDetails = forumServiceImpl.getPostInfoById(id);
        responseJson.setSuccessResPonse(postDetails);
        return responseJson;
    }
    @PostMapping("/getPostinfoBypage")
    public ResponseJson getPostDetailsByPage( @RequestBody PostDeatailsDTO postDeatailsDTO){
        List<PostDetails> postDetails=forumServiceImpl.getPostDetailsByPage(postDeatailsDTO);
        responseJson.setSuccessResPonse(postDetails);
        return responseJson;
    }

    /**
     * @Param: [postsReport]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/7 19:33
     * 添加举报信息
     */
    @PostMapping("/postsReport")
    public ResponseJson insertPostsReport(@RequestBody PostsReport postsReport) {
        int flag = forumServiceImpl.insertPostsReport(postsReport);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [postsComment]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 18:30
     * 添加评论记录
     */
    @PostMapping("/postsComment")
    public ResponseJson insertPostsComment(@RequestBody PostsComment postsComment) {
        int flag = forumServiceImpl.insertPostsComment(postsComment);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 20:12
     * 删除评论--联级删除
     */
    @DeleteMapping("/postsComment")
    public ResponseJson delPostsCommentById(String id) {
        int flag = forumServiceImpl.delPostsCommentById(id);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /***
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 22:23
     * 根据帖子id查看评论
     */
    @GetMapping("/postsComment")
    public ResponseJson getCommentByPostsId(String id) {
        List<CurrentComment> commentReplies = forumServiceImpl.getCommentByPostsId(id);
        responseJson.setSuccessResPonse(commentReplies);
        return responseJson;
    }

    /**
     * @Param: [postsReply]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 18:45
     * 添加评论回复
     */
    @PostMapping("/postsReply")
    public ResponseJson insertPostsReply(@RequestBody PostsReply postsReply) {
        int flag = forumServiceImpl.insertPostsReply(postsReply);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/6 19:20
     * 删除评论回复
     */
    @DeleteMapping("/postsReply")
    public ResponseJson delPostsReplyById(String id) {
        int flag = forumServiceImpl.delPostsReplyById(id);
        responseJson.setResPonse(flag);
        return responseJson;
    }
/**
 * @Param: [id]
 * @Return: pers.lcf.rents.utils.ResponseJson
 * @Author: lcf
 * @Date: 2019/10/8 9:08
 * 根据评论id获取回复
 */
    @GetMapping("/postsReply")
    public ResponseJson getReplyByCommentId(String id) {
        List<PostsReply> postsReplies = forumServiceImpl.getReplyByCommentId(id);
        responseJson.setSuccessResPonse(postsReplies);
        return responseJson;
    }
}
