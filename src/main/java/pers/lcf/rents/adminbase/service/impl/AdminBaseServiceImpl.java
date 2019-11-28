package pers.lcf.rents.adminbase.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.adminbase.mapper.UserRealNameMapper;
import pers.lcf.rents.adminbase.model.*;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.forum.mapper.PostsInfoMapper;
import pers.lcf.rents.forum.mapper.PostsReportMapper;

import pers.lcf.rents.forum.model.PostsReport;
import pers.lcf.rents.forum.model.PostsReportExample;
import pers.lcf.rents.userbase.mapper.UserInfoMapper;
import pers.lcf.rents.userbase.mapper.UserLoginMapper;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserInfoExample;
import pers.lcf.rents.userbase.model.UserLogin;
import pers.lcf.rents.userbase.model.UserLoginExample;
import pers.lcf.rents.userbase.service.UserBaseService;
import pers.lcf.rents.utils.BaseConstant;
import pers.lcf.rents.utils.HttpUtils;
import pers.lcf.rents.utils.RentsUtil;
import pers.lcf.rents.utils.ResponseJson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.util.*;
import java.util.logging.Logger;

/**
 * @ClassName AdminBaseServiceImpl
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/5 12:46
 **/
@Service
public class AdminBaseServiceImpl implements AdminBaseService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserBaseService userBaseServiceImpl;
    @Autowired
    private PostsReportMapper postsReportMapper;
    @Autowired
    private UserRealNameMapper userRealNameMapper;

    @Autowired
    private PostsInfoMapper postsInfoMapper;


    /**
     * @Param: [postsReportDTO]
     * @Return: pers.lcf.rents.adminbase.model.PostsReportDTO
     * @Author: lcf
     * @Date: 2019/11/16 13:48
     * 举报记录分页
     */
    @Override
    public PostsReportDTO getPostsReportsByDTO(PostsReportDTO postsReportDTO) {
        int[] startEnd = PageUtil.transToStartEnd(postsReportDTO.getPageNo(), postsReportDTO.getPageSize());
        Report report = postsReportDTO.getReports().get(0);

//        判断时间范围，是否有选中时间
        String times[] = {report.getGmtCreateBegin(), report.getGmtCreateEnd()};
        String selectTime[] = RentsUtil.selectTime(times);
        report.setGmtCreateBegin(selectTime[0]);
        report.setGmtCreateEnd(selectTime[1]);

        List<List<?>> dtoList = postsReportMapper.getPostsReportsByDTO(report, startEnd[0], startEnd[1]);
        List<Report> reports = (List<Report>) dtoList.get(0);
        PostsReportDTO dto = new PostsReportDTO();
        dto.setReports(reports);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, postsReportDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(postsReportDTO.getPageNo());
        dto.setPageSize(postsReportDTO.getPageSize());
        return dto;
//        return null;
    }

    /**
     * @Param: [report]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/13 20:55
     * 帖子举报修改
     */
    @Override
    public Integer updatePostsReports(Report report) {
        if (report.getHasHandle() == BaseConstant.YES_HAS_HANDLE) {
            return 0;
        }

        PostsReport postsReport = new PostsReport();
        postsReport.setHasHandle(BaseConstant.YES_HAS_HANDLE);
        postsReport.setGmtModified(DateUtil.now());
        PostsReportExample examplepr = new PostsReportExample();
        PostsReportExample.Criteria criteriapr = examplepr.createCriteria();
        criteriapr.andIdEqualTo(report.getId());
        Integer flagpr = postsReportMapper.updateByExampleSelective(postsReport, examplepr);
        if (flagpr <= 0) {
            return 0;
        }


        UserInfo userInfo = new UserInfo();
        userInfo.setReportSum(report.getReportSum() + 1);
        userInfo.setGmtModified(DateUtil.now());
        UserInfoExample exampleui = new UserInfoExample();
        UserInfoExample.Criteria criteriaui = exampleui.createCriteria();
        criteriaui.andIdEqualTo(report.getReleaseId());
        Integer flagui = userInfoMapper.updateByExampleSelective(userInfo, exampleui);

        return flagui;
    }

    /**
     * @Param: [ids]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/16 14:28
     * 帖子举报信息批量删除
     */
    @Override
    public Integer delPostsReportById(List<String> ids) {
        Integer flag = postsReportMapper.delPostsReportById(ids);
        return flag;
    }

    /**
     * @Param: [userRealNameDTO]
     * @Return: pers.lcf.rents.adminbase.model.UserRealNameDTO
     * @Author: lcf
     * @Date: 2019/11/16 15:59
     * 用户实名记录分页
     */
    @Override
    public UserRealNameDTO getUserRealNamesByDTO(UserRealNameDTO userRealNameDTO) {
        int[] startEnd = PageUtil.transToStartEnd(userRealNameDTO.getPageNo(), userRealNameDTO.getPageSize());
        UserRealName userRealName = userRealNameDTO.getUserRealNames().get(0);

//        判断时间范围，是否有选中时间
        String times[] = {userRealName.getGmtCreateBegin(), userRealName.getGmtCreateEnd()};
        String selectTime[] = RentsUtil.selectTime(times);
        userRealName.setGmtCreateBegin(selectTime[0]);
        userRealName.setGmtCreateEnd(selectTime[1]);

        List<List<?>> dtoList = userRealNameMapper.getUserRealNamesByDTO(userRealName, startEnd[0], startEnd[1]);
        List<UserRealName> userRealNames = (List<UserRealName>) dtoList.get(0);
        UserRealNameDTO dto = new UserRealNameDTO();
        dto.setUserRealNames(userRealNames);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, userRealNameDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(userRealNameDTO.getPageNo());
        dto.setPageSize(userRealNameDTO.getPageSize());
        return dto;
//        return null;
    }

    /**
     * @Param: [userRealName]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/16 21:54
     * 用户审核记录信息修改
     */
    @Override
    public Integer updateUserRealNames(UserRealName userRealName) {
        if (userRealName.getHasHandle() == BaseConstant.YES_HAS_HANDLE) {
            return 0;
        }
        UserRealName userReal = new UserRealName();
        userReal.setHasHandle(BaseConstant.YES_HAS_HANDLE);
        userReal.setGmtModified(DateUtil.now());
        UserRealNameExample exampleReal = new UserRealNameExample();
        UserRealNameExample.Criteria criteria = exampleReal.createCriteria();
        criteria.andIdEqualTo(userRealName.getId());
        Integer flagReal = userRealNameMapper.updateByExampleSelective(userReal, exampleReal);
        if (flagReal <= 0) {
            return 0;
        }


        UserInfo userInfo=new UserInfo();
        userInfo.setHasRealName(BaseConstant.YES_REAL_NAME);
        userInfo.setGmtModified(DateUtil.now());
        UserInfoExample exampleinfo=new UserInfoExample();
        UserInfoExample.Criteria criteriainfo=exampleinfo.createCriteria();
        criteriainfo.andIdEqualTo(userRealName.getUserInfoId());
        Integer flagInfo=userInfoMapper.updateByExampleSelective(userInfo,exampleinfo);

        return flagInfo;
    }

    /**
     * @Param: [ids]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/16 22:35
     * 用户审核批量删除
     */
    @Override
    public Integer delUserRealNameById(List<String> ids) {
       Integer flag= userRealNameMapper.delUserRealNameById(ids);
        return flag;
    }
/**
 * @Param: [postDetailsDTO]
 * @Return: pers.lcf.rents.adminbase.model.PostDetailsDTO
 * @Author: lcf
 * @Date: 2019/11/19 11:28
 * 帖子管理分页
 */
    @Override
    public PostDetailsDTO getPostsInfoByDTO(PostDetailsDTO postDetailsDTO) {
        int[] startEnd = PageUtil.transToStartEnd(postDetailsDTO.getPageNo(), postDetailsDTO.getPageSize());
        PostsInfoDetails postDetails=null;
        try {
           postDetails = postDetailsDTO.getPostsInfoDetailses().get(0);
        }catch (Exception e){
            System.out.println("空指针");
        }


//        判断时间范围，是否有选中时间
        String times[] = {postDetails.getGmtCreateBegin(), postDetails.getGmtCreateEnd()};
        String selectTime[] = RentsUtil.selectTime(times);
        postDetails.setGmtCreateBegin(selectTime[0]);
        postDetails.setGmtCreateEnd(selectTime[1]);

        List<List<?>> dtoList =postsInfoMapper.getPostsInfoByDTO(postDetails, startEnd[0], startEnd[1]);
        List<PostsInfoDetails> postDetailses = (List<PostsInfoDetails>) dtoList.get(0);
        PostDetailsDTO dto = new PostDetailsDTO();
        dto.setPostsInfoDetailses(postDetailses);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, postDetailsDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(postDetailsDTO.getPageNo());
        dto.setPageSize(postDetailsDTO.getPageSize());
        return dto;
//        return null;
    }

    @Override
    public ResponseJson adminUserLogin(UserLogin userLogin) {
        UserLoginExample example=new UserLoginExample();
        UserLoginExample.Criteria criteria=example.createCriteria();
        criteria.andLoginNameEqualTo(userLogin.getLoginName());
        criteria.andPasswordEqualTo(userLogin.getPassword());
        List<UserLogin>  userLogins= userLoginMapper.selectByExample(example);
        ResponseJson responseJson=new ResponseJson();
        Map<String,String> tokenMap=CollUtil.newHashMap();
        tokenMap.put("token",IdUtil.simpleUUID());
        if(CollUtil.isEmpty(userLogins)){
            responseJson.setResPonseSelfMsg("用户名密码错误/用户不存在");
//            responseJson.setErrorResPonse(tokenMap,"用户名密码错误/用户不存在");
            return responseJson;
        }
        UserLogin user=userLogins.get(0);
        if(!user.getIsState().equals(BaseConstant.IS_STATE)){
            responseJson.setResPonseSelfMsg("账号已被封");
//            responseJson.setErrorResPonse(tokenMap,"账号已被封");
            return responseJson;
        }

        UserInfo userInfo = userInfoMapper.getUserInfoByLoginId(user.getLoginName());
        userInfo.setToken( IdUtil.simpleUUID());
        responseJson.setSuccessResPonse(userInfo);
        return responseJson;
    }

    @Override
    public UserInfo getAdminInfoById(String id) {
        UserInfoExample example=new UserInfoExample();
        UserInfoExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(id);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if(CollUtil.isEmpty(userInfos)){
            return null;
        }
        userInfos.get(0).setToken( IdUtil.simpleUUID());
        return  userInfos.get(0);
    }

    @Override
    public ResponseJson realName() {

        String host = "http://dm-51.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_idcard.json";
        String appcode = BaseConstant.APP_CODE;
        String imgFile = "F:\\正面.jpg";
        Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        //            configObj.put("min_size", 5);
        //            String config_str = "";

        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            File file = new File(imgFile);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return null;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if(is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
                System.out.println(out.toJSONString());
            }else{
                System.out.println(res_obj.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Param: [type, dataValue]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Author: lcf
     * @Date: 2019/11/29 0:27
     * 证件识别 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @Param: [userDTO]
     * @Return: pers.lcf.rents.adminbase.model.UserDTO
     * @Author: lcf
     * @Date: 2019/11/9 14:27
     * 普通用户分页查询
     */
    @Override
    public UserDTO getOrdinaryUsersByDTO(UserDTO userDTO) {
        UserDTO dto = getuserDTO(userDTO, "会员用户");
        return dto;
    }

    /**
     * @Param: [userDTO]
     * @Return: pers.lcf.rents.adminbase.model.UserDTO
     * @Author: lcf
     * @Date: 2019/11/10 12:35
     * 管理员分页查询
     */
    @Override
    public UserDTO getAdminUsersDTO(UserDTO userDTO) {
        UserDTO dto = getuserDTO(userDTO, "管理员");
        return dto;
    }

    /**
     * @Param: [user]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/9 14:26
     * 普通用户信息修改
     */
    @Override
    public Integer updateOrdinaryUser(User user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);    //转换
        int flagUi = userBaseServiceImpl.updataUserInfoById(userInfo);
        if (flagUi <= 0) {
            return flagUi;
        }
        UserLoginExample exampleUl = new UserLoginExample();
        UserLoginExample.Criteria criteriaUl = exampleUl.createCriteria();
        criteriaUl.andLoginNameEqualTo(user.getLoginName());
        UserLogin userLogin = new UserLogin();
        BeanUtils.copyProperties(user, userLogin);
        userLogin.setGmtModified(DateUtil.now());
        int flagUl = userLoginMapper.updateByExampleSelective(userLogin, exampleUl);
        return flagUl;
    }

    /**
     * @Param: [ids]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/9 16:41
     * 根据id 批量删除普通用户信息
     */
    @Override
    public Integer delOrdinaryUserById(List<String> ids) {
        int flag = userInfoMapper.delOrdinaryUserById(ids);
        return flag;

    }

    /**
     * @Param: []
     * @Return: pers.lcf.rents.adminbase.model.OrdinaryUsersPei
     * @Author: lcf
     * @Date: 2019/11/16 13:45
     * 获取饼图数据
     */
    @Override
    public OrdinaryUsersPei getOrdinaryUsersPei() {
        OrdinaryUsersPei ordinaryUsersPei = userInfoMapper.getOrdinaryUsersPei();

//        List<Map<String, Integer>> sex= CollUtil.newArrayList();

        Map<String, Integer> sex = CollUtil.newHashMap();
        sex.put("男", ordinaryUsersPei.getMan());
        sex.put("女", ordinaryUsersPei.getWoman());
        ordinaryUsersPei.setSex(sex);

        Map<String, Integer> isState = CollUtil.newHashMap();
        isState.put("正常", ordinaryUsersPei.getNormal());
        isState.put("封号", ordinaryUsersPei.getUnnormal());
        ordinaryUsersPei.setIsState(isState);

        Map<String, Integer> hasRealName = CollUtil.newHashMap();
        hasRealName.put("实名", ordinaryUsersPei.getYesreal());
        hasRealName.put("未实名", ordinaryUsersPei.getNoreal());
        ordinaryUsersPei.setHasRealName(hasRealName);
        return ordinaryUsersPei;
    }

    private UserDTO getuserDTO(UserDTO userDTO, String type) {
        int[] startEnd = PageUtil.transToStartEnd(userDTO.getPageNo(), userDTO.getPageSize());
        User user = userDTO.getUsers().get(0);
        user.setUserTypeName(type);
//        判断时间范围，是否有选中时间
        String times[] = {user.getGmtCreateBegin(), user.getGmtCreateEnd()};
        String selectTime[] = RentsUtil.selectTime(times);
        user.setGmtCreateBegin(selectTime[0]);
        user.setGmtCreateEnd(selectTime[1]);

        List<List<?>> dtoList = userInfoMapper.getOrdinaryUsersByDTO(user, startEnd[0], startEnd[1]);
        List<User> users = (List<User>) dtoList.get(0);
        UserDTO dto = new UserDTO();
        dto.setUsers(users);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, userDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(userDTO.getPageNo());
        dto.setPageSize(userDTO.getPageSize());
        return dto;
    }



}
