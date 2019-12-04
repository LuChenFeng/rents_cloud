package pers.lcf.rents.adminbase.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PageUtil;

import cn.hutool.json.JSONUtil;
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
import pers.lcf.rents.utils.*;


import java.util.Map;


import java.util.*;


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

    @Autowired
    private ResponseJson responseJson;


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
//    @Override
//    public UserRealNameDTO getUserRealNamesByDTO(UserRealNameDTO userRealNameDTO) {
//        int[] startEnd = PageUtil.transToStartEnd(userRealNameDTO.getPageNo(), userRealNameDTO.getPageSize());
//        UserRealName userRealName = userRealNameDTO.getUserRealNames().get(0);
//
////        判断时间范围，是否有选中时间
//        String times[] = {userRealName.getGmtCreateBegin(), userRealName.getGmtCreateEnd()};
//        String selectTime[] = RentsUtil.selectTime(times);
//        userRealName.setGmtCreateBegin(selectTime[0]);
//        userRealName.setGmtCreateEnd(selectTime[1]);
//
//        List<List<?>> dtoList = userRealNameMapper.getUserRealNamesByDTO(userRealName, startEnd[0], startEnd[1]);
//        List<UserRealName> userRealNames = (List<UserRealName>) dtoList.get(0);
//        UserRealNameDTO dto = new UserRealNameDTO();
//        dto.setUserRealNames(userRealNames);
//        List<Integer> count = (List<Integer>) dtoList.get(1);
//        int totalCount = count.get(0);
//        int totalPage = PageUtil.totalPage(totalCount, userRealNameDTO.getPageSize());
//        dto.setTotalCount(totalCount);
//        dto.setTotalPage(totalPage);
//        dto.setPageNo(userRealNameDTO.getPageNo());
//        dto.setPageSize(userRealNameDTO.getPageSize());
//        return dto;
////        return null;
//    }

    /**
     * @Param: [userRealName]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/16 21:54
     * 用户审核记录信息修改
     */
//    @Override
//    public Integer updateUserRealNames(UserRealName userRealName) {
//        if (userRealName.getHasHandle() == BaseConstant.YES_HAS_HANDLE) {
//            return 0;
//        }
//        UserRealName userReal = new UserRealName();
//        userReal.setHasHandle(BaseConstant.YES_HAS_HANDLE);
//        userReal.setGmtModified(DateUtil.now());
//        UserRealNameExample exampleReal = new UserRealNameExample();
//        UserRealNameExample.Criteria criteria = exampleReal.createCriteria();
//        criteria.andIdEqualTo(userRealName.getId());
//        Integer flagReal = userRealNameMapper.updateByExampleSelective(userReal, exampleReal);
//        if (flagReal <= 0) {
//            return 0;
//        }
//
//
//        UserInfo userInfo=new UserInfo();
//        userInfo.setHasRealName(BaseConstant.YES_REAL_NAME);
//        userInfo.setGmtModified(DateUtil.now());
//        UserInfoExample exampleinfo=new UserInfoExample();
//        UserInfoExample.Criteria criteriainfo=exampleinfo.createCriteria();
//        criteriainfo.andIdEqualTo(userRealName.getUserInfoId());
//        Integer flagInfo=userInfoMapper.updateByExampleSelective(userInfo,exampleinfo);
//
//        return flagInfo;
//    }

    /**
     * @Param: [ids]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/16 22:35
     * 用户审核批量删除
     */
//    @Override
//    public Integer delUserRealNameById(List<String> ids) {
//       Integer flag= userRealNameMapper.delUserRealNameById(ids);
//        return flag;
//    }

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
        PostsInfoDetails postDetails = null;
        try {
            postDetails = postDetailsDTO.getPostsInfoDetailses().get(0);
        } catch (Exception e) {
            System.out.println("空指针");
        }


//        判断时间范围，是否有选中时间
        String times[] = {postDetails.getGmtCreateBegin(), postDetails.getGmtCreateEnd()};
        String selectTime[] = RentsUtil.selectTime(times);
        postDetails.setGmtCreateBegin(selectTime[0]);
        postDetails.setGmtCreateEnd(selectTime[1]);

        List<List<?>> dtoList = postsInfoMapper.getPostsInfoByDTO(postDetails, startEnd[0], startEnd[1]);
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
        UserLoginExample example = new UserLoginExample();
        UserLoginExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(userLogin.getLoginName());
        criteria.andPasswordEqualTo(userLogin.getPassword());
        List<UserLogin> userLogins = userLoginMapper.selectByExample(example);
        ResponseJson responseJson = new ResponseJson();
        Map<String, String> tokenMap = CollUtil.newHashMap();
        tokenMap.put("token", IdUtil.simpleUUID());
        if (CollUtil.isEmpty(userLogins)) {
            responseJson.setResPonseSelfMsg("用户名密码错误/用户不存在");
//            responseJson.setErrorResPonse(tokenMap,"用户名密码错误/用户不存在");
            return responseJson;
        }
        UserLogin user = userLogins.get(0);
        if (!user.getIsState().equals(BaseConstant.IS_STATE)) {
            responseJson.setResPonseSelfMsg("账号已被封");
//            responseJson.setErrorResPonse(tokenMap,"账号已被封");
            return responseJson;
        }

        UserInfo userInfo = userInfoMapper.getUserInfoByLoginId(user.getLoginName());
        userInfo.setToken(IdUtil.simpleUUID());
        responseJson.setSuccessResPonse(userInfo);
        return responseJson;
    }

    @Override
    public UserInfo getAdminInfoById(String id) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (CollUtil.isEmpty(userInfos)) {
            return null;
        }
        userInfos.get(0).setToken(IdUtil.simpleUUID());
        return userInfos.get(0);
    }

    @Override
    public ResponseJson realName(RealNameVO realNameVO, String fileSon) {
        List<String> imgs = realNameVO.getImgUrls();
        String[] side = {"face", "back"};
        List<JSONObject> returnJsons = CollUtil.newArrayList();
        //读出证件信息
        if (imgs.size() < 2) {
            responseJson.setResPonseSelfMsg("请同时上传正面、反面照片");
            return responseJson;
        }
        for (int i = 0; i < imgs.size(); i++) {
            String imgUrl = imgs.get(i);
            String[] strs = imgUrl.split("/");
            String flieUrl = BaseConstant.IMG_PATH + fileSon + "/" + strs[strs.length - 1];
            JSONObject returnJson = RealNameUtil.peopleIdDistinguish(flieUrl, side[i]);
            returnJsons.add(returnJson);
            FileUtil.delFile(flieUrl);
        }

//        转换成bean
        if (returnJsons.get(0) == null || returnJsons.get(1) == null) {
            responseJson.setResPonseSelfMsg("正面/反面识别失败，请从新上传");
            return responseJson;
        }
        UserRealName face = JSONObject.parseObject(String.valueOf(returnJsons.get(0)), UserRealName.class);
        UserRealName back = JSONObject.parseObject(String.valueOf(returnJsons.get(1)), UserRealName.class);

        UserRealName realName = face;
        realName.setId(IdUtil.simpleUUID());
        realName.setUserInfoId(realNameVO.getUserInfoId());
        realName.setBirth(strFormat(face.getBirth()));
        realName.setStartDate(strFormat(back.getStartDate()));
        realName.setEndDate(strFormat(back.getEndDate()));
        realName.setIssue(back.getIssue());
        realName.setGmtCreate(DateUtil.now());
        realName.setGmtModified(DateUtil.now());

//        存入数据库
        UserRealNameExample exampleReal = new UserRealNameExample();
        UserRealNameExample.Criteria criteriaReal = exampleReal.createCriteria();
        criteriaReal.andNumEqualTo(realName.getNum());
        List<UserRealName> userRealNames = userRealNameMapper.selectByExample(exampleReal);
        if (!CollUtil.isEmpty(userRealNames)) {
            responseJson.setResPonseSelfMsg("该证件已被实名");
            return responseJson;
        }
        int flagReal = userRealNameMapper.insertSelective(realName);
        if (flagReal <= 0) {
            responseJson.setResPonseSelfMsg("实名录入失败");
            return responseJson;
        }
        //修改用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setHasRealName(BaseConstant.YES_REAL_NAME);
        userInfo.setBirthady(realName.getBirth());
        userInfo.setSex(realName.getSex());
        userInfo.setGmtModified(DateUtil.now());
        UserInfoExample exampleInfo = new UserInfoExample();
        UserInfoExample.Criteria criteriaInfo = exampleInfo.createCriteria();
        criteriaInfo.andIdEqualTo(realName.getUserInfoId());
        int flag = userInfoMapper.updateByExampleSelective(userInfo, exampleInfo);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    @Override
    public List<UserRealName> getRealNameByUserInfoId(String id) {
        UserRealNameExample example = new UserRealNameExample();
        UserRealNameExample.Criteria criteria = example.createCriteria();
        criteria.andUserInfoIdEqualTo(id);
        List<UserRealName> userRealNames = userRealNameMapper.selectByExample(example);
        return userRealNames;
    }



    private String strFormat(String date) {
        String str = String.format("%s-%s-%s", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
        return str;
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
        userDTO.getUsers().get(0).setUserTypeNameTwo("超级管理员");
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
