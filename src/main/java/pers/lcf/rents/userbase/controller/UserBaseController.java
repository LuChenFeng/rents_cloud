package pers.lcf.rents.userbase.controller;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserMatch;
import pers.lcf.rents.userbase.model.UserStyle;
import pers.lcf.rents.userbase.service.UserBaseService;
import pers.lcf.rents.utils.FileUtil;
import pers.lcf.rents.utils.ResponseJson;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName UserBaseController
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/3 20:47
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserBaseController {
    @Autowired
    private UserBaseService userBaseServiceImpl;

    @Autowired
    private  ResponseJson responseJson;
    /**
     * @Param: [userStyle]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/4 8:45
     * 用户特点记录插入
     */
    @PostMapping("/style")
    public ResponseJson insertUserStyle (@RequestBody UserStyle userStyle){
       int flag= userBaseServiceImpl.insertUserStyle(userStyle);
       responseJson.setResPonse(flag);
       return responseJson;
    }

    /**
     * @Param: [userStyle]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/4 9:45
     * 用户特点修改
     */
    @PutMapping("/style")
    public ResponseJson updateUserStyle(@RequestBody UserStyle userStyle) {
        int flag=userBaseServiceImpl.updateUserStyle(userStyle);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [userInfoId]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/4 10:21
     * 根据userInfoId 查找用户特点记录
     */
    @GetMapping("/style")
    public ResponseJson getStyleByUserInfoId( String userInfoId) {
        List<UserStyle> userStyles=userBaseServiceImpl.getStyleByUserInfoId(userInfoId);
        responseJson.setSuccessResPonse(userStyles);
        return responseJson;
    }

    @GetMapping("/matching")
    public ResponseJson getRentsByLoadnum(int loadNum,String userInfoId){
        List<UserStyle> userStyles=userBaseServiceImpl.getStyleByUserInfoId(userInfoId);
        if(CollUtil.isEmpty(userStyles)){
            responseJson.setResPonseSelfMsg("先记录下你的特点");
            return responseJson;
        }
       List<UserMatch> matchStyles= userBaseServiceImpl.getRentsByLoadnum(loadNum,userStyles);
        responseJson.setSuccessResPonse(matchStyles);
        return responseJson;
    }

    /**
     * @Param: [file, request]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/5 20:09
     * 头像上传
     */
    //处理单个文件上传
    @RequestMapping(value="/uploadimg", method = RequestMethod.POST)
    public  ResponseJson uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String info = null;
        String fileSon="avatarImage/";
        List<String> imageUrl= CollUtil.newArrayList();
        try {
            info = FileUtil.uploadFile(file,fileSon);
            imageUrl.add(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseJson.setSuccessResPonse(imageUrl);
        return responseJson;
    }

}
