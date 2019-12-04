package pers.lcf.rents.userbase.controller;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.lcf.rents.userbase.model.*;
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
    private ResponseJson responseJson;

    /**
     * @Param: [userInfo]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/2 17:03
     * 用户信息修改
     */
    @PostMapping("/userInfo")
    public ResponseJson updataUserInfoById(@RequestBody UserInfo userInfo) {
        int flag = userBaseServiceImpl.updataUserInfoById(userInfo);
        if (flag <= 0) {
            responseJson.setResPonse(flag);
            return responseJson;
        }
        List<UserInfo> userInfos = userBaseServiceImpl.getusreInfoById(userInfo.getId());
        responseJson.setSuccessResPonse(userInfos);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/19 22:30
     * 获取用户信息
     */
    @GetMapping("/userInfo")
    public ResponseJson getUserInfoById(String id) {
        List<UserInfo> userInfos = userBaseServiceImpl.getusreInfoById(id);
        responseJson.setSuccessResPonse(userInfos);
        return responseJson;
    }

    /**
     * @Param: [userId]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/2 17:03
     * 根据用户id返回用户详情页信息
     */
    @GetMapping("/userInfoStyle")
    public ResponseJson getUserInfoStyleById(String id) {
        List<UserMatch> userMatches = userBaseServiceImpl.getUserInfoStyleById(id);
        if (CollUtil.isEmpty(userMatches)) {
            responseJson.setResPonseSelfMsg("获取失败");
            return responseJson;
        }
        responseJson.setSuccessResPonse(userMatches);
        return responseJson;
    }

    /**
     * @Param: [userStyle]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/4 8:45
     * 用户特点记录插入
     */
    @PostMapping("/style")
    public ResponseJson insertUserStyle(@RequestBody UserStyle userStyle) {
        int flag = userBaseServiceImpl.insertUserStyle(userStyle);
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
        int flag = userBaseServiceImpl.updateUserStyle(userStyle);
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
    public ResponseJson getStyleByUserInfoId(String userInfoId) {
        List<UserStyle> userStyles = userBaseServiceImpl.getStyleByUserInfoId(userInfoId);
        responseJson.setSuccessResPonse(userStyles);
        return responseJson;
    }

    /**
     * @Param: [loadNum, userInfoId]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/1 19:20
     * 匹配用户
     */
    @GetMapping("/matching")
    public ResponseJson getRentsByLoadnum(int loadNum, String userInfoId) {
        List<UserStyle> userStyles = userBaseServiceImpl.getStyleByUserInfoId(userInfoId);
        if (CollUtil.isEmpty(userStyles)) {
            responseJson.setResPonseSelfMsg("先记录下你的特点");
            return responseJson;
        }
        List<UserMatch> matchStyles = userBaseServiceImpl.getRentsByLoadnum(loadNum, userStyles);
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
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public ResponseJson uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String info = null;
        String fileSon = "avatarImage/";
        List<String> imageUrl = CollUtil.newArrayList();
        try {
            info = FileUtil.uploadFile(file, fileSon);
            imageUrl.add(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseJson.setSuccessResPonse(imageUrl);
        return responseJson;
    }

    /**
     * @Param: [userLoginAppInfo]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/1 19:17
     * 用户登入
     */
    @PostMapping("/userLoginByAppInfo")
    public ResponseJson userLoginByAppInfo(@RequestBody UserLoginAppInfo userLoginAppInfo) {
        ResponseJson responseJson = userBaseServiceImpl.userLoginByAppInfo(userLoginAppInfo);
        return responseJson;
    }

    /**
     * @Param: [userLoginAppInfo]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/1 19:17
     * 用户注册
     */
    @PostMapping("/userRegisteredByAppInfo")
    public ResponseJson userRegisteredByAppInfo(@RequestBody UserLoginAppInfo userLoginAppInfo) {
        ResponseJson responseJson = userBaseServiceImpl.userRegisteredByAppInfo(userLoginAppInfo);
        return responseJson;
    }

    /**
     * @Param: [date, userInfoId]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/12/3 17:02
     * 获取用户出差
     */
    @GetMapping("/userOuts")
    public ResponseJson getUserOutsByMonth(String date, String userInfoId) {
        List<RentsOut> rentsOuts = userBaseServiceImpl.getUserOutsByMonth(date, userInfoId);
        responseJson.setSuccessResPonse(rentsOuts);
        return responseJson;
    }

    /**
     * @Param: [rentsOut]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/12/3 17:03
     * 添加出差记录
     */
    @PostMapping("/userOuts")
    public ResponseJson addUserOuts(@RequestBody RentsOut rentsOut) {
        Integer flag = userBaseServiceImpl.addUserOuts(rentsOut);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [id]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/12/3 17:04
     * 删除出差
     */
    @DeleteMapping("/userOuts")
    public ResponseJson delUserOut(String id) {
        Integer flag = userBaseServiceImpl.delUserOut(id);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    @GetMapping("/userPassword")
    public ResponseJson updatePwdByUserInfoId(String id, String passwordOld, String password) {
        responseJson = userBaseServiceImpl.updatePwdByUserInfoId(id, passwordOld, password);
        return responseJson;
    }

    @PostMapping("/userPwd")
    public  ResponseJson updateUserPwd(@RequestBody UserLoginAppInfo userLoginAppInfo){
        responseJson=userBaseServiceImpl.updateUserPwd(userLoginAppInfo);
        return  responseJson;
    }
}