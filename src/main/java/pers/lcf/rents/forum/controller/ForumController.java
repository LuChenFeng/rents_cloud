package pers.lcf.rents.forum.controller;



import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import pers.lcf.rents.forum.model.PostsType;
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
    @RequestMapping(value="/uploadimgs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson multipleFilesUpload(HttpServletRequest request) {

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> imageUrl= CollUtil.newArrayList();
        String fileSon="postsImage/";
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();

            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                String s = FileUtil.uploadFile( entry.getValue(),fileSon);
                imageUrl.add(s);
            }
        }
        responseJson.setSuccessResPonse(imageUrl);
        return responseJson;
    }

    @GetMapping("/selectTypeByIsHave")
    public ResponseJson selectTypeByIsHave(Byte isHave){
        List<PostsType> postsTypes= forumServiceImpl.selectTypeByIsHave(isHave);
        responseJson.setSuccessResPonse(postsTypes);
        return  responseJson;
    }

}
