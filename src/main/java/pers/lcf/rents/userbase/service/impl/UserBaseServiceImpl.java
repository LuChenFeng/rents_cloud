package pers.lcf.rents.userbase.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.userbase.mapper.UserInfoMapper;
import pers.lcf.rents.userbase.mapper.UserLoginMapper;
import pers.lcf.rents.userbase.mapper.UserStyleMapper;
import pers.lcf.rents.userbase.mapper.UserTypeMapper;
import pers.lcf.rents.userbase.model.*;
import pers.lcf.rents.userbase.service.UserBaseService;
import pers.lcf.rents.utils.BaseConstant;
import pers.lcf.rents.utils.ResponseJson;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName UserBaseServiceImpl
 * @Deacription 用户基本功能
 * @Author lcf
 * @Date 2019/10/3 20:37
 **/

@Service
public class UserBaseServiceImpl implements UserBaseService {

    @Autowired
    private ResponseJson responseJson;
    @Autowired
    private UserStyleMapper userStyleMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserTypeMapper userTypeMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * @Param: [userStyle]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/4 9:28
     * 用户特点记录添加
     */
    @Override
    public Integer insertUserStyle(UserStyle userStyle) {
        String id = IdUtil.simpleUUID();//hutool生成id
        userStyle.setId(id);
        int styleSum = userStyle.getThought() + userStyle.getThinking() + userStyle.getSpend()
                + userStyle.getNature() + userStyle.getDiscipline();
        userStyle.setStyleSum(styleSum);
        userStyle.setGmtCreate(DateUtil.now());
        userStyle.setGmtModified(DateUtil.now());

        int flag = userStyleMapper.insert(userStyle);
        return flag;
    }

    /**
     * @Param: [userStyle]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/4 9:30
     * 用户特点记录修改
     */
    @Override
    public Integer updateUserStyle(UserStyle userStyle) {
        UserStyleExample example = new UserStyleExample();
        UserStyleExample.Criteria criteria = example.createCriteria();
//        criteria.andIdEqualTo(userStyle.getId());
        criteria.andUserInfoIdEqualTo(userStyle.getUserInfoId());
        Integer styleSum = userStyle.getThought() + userStyle.getThinking() + userStyle.getSpend()
                + userStyle.getNature() + userStyle.getDiscipline();
        userStyle.setStyleSum(styleSum);
        userStyle.setGmtModified(DateUtil.now());

        Integer flag = userStyleMapper.updateByExampleSelective(userStyle, example);
        return flag;
    }

    /**
     * @Param: [userInfoId]
     * @Return: java.util.List<pers.lcf.rents.userbase.model.UserStyle>
     * @Author: lcf
     * @Date: 2019/10/4 14:00
     * 根据getStyleByUserInfoId 查用户特点记录
     */
    @Override
    public List<UserStyle> getStyleByUserInfoId(String userInfoId) {
        UserStyleExample example = new UserStyleExample();
        UserStyleExample.Criteria criteria = example.createCriteria();
        criteria.andUserInfoIdEqualTo(userInfoId);
        List<UserStyle> userStyles = userStyleMapper.selectByExample(example);
        return userStyles;
    }

    /**
     * @Param: [loadNum, userInfoId]
     * @Return: java.util.List<pers.lcf.rents.userbase.model.UserInfo>
     * @Author: lcf
     * @Date: 2019/10/4 11:37
     * 获取匹配用户
     */
    @Override
    public List<UserMatch> getRentsByLoadnum(int loadNum, List<UserStyle> userStyles) {
        int sytleSum = 0;
        String city = null;
        String userInfoId = null;
        List<Integer> styleFractions = null; //改用户性格指数列表
        Iterator<UserStyle> iterator = userStyles.iterator();
        while (iterator.hasNext()) {
            UserStyle cStyle = iterator.next();
            sytleSum = cStyle.getStyleSum();
            city = cStyle.getCity();
            userInfoId = cStyle.getUserInfoId();
            styleFractions = CollUtil.newArrayList(cStyle.getThought(), cStyle.getSpend()
                    , cStyle.getNature(), cStyle.getDiscipline(), cStyle.getThinking());
        }

        //找到同一城市且styleSum在一定范围内的用户
        List<String> userInfoIds = CollUtil.newArrayList();
        userInfoIds.add(userInfoId);
        UserStyleExample example = new UserStyleExample();
        UserStyleExample.Criteria criteria = example.createCriteria();
        criteria.andUserInfoIdNotIn(userInfoIds);
        criteria.andCityEqualTo(city);
        criteria.andStyleSumBetween(sytleSum - (BaseConstant.MATCH_STEP * loadNum), sytleSum + (BaseConstant.MATCH_STEP * loadNum));
        List<UserMatch> matchStyles = userStyleMapper.selectUserMatchByExample(example);

        //计算和取出的每个用户的相似度
        Iterator<UserMatch> itUserMatch = matchStyles.iterator();
        while (itUserMatch.hasNext()) {
            UserMatch userMatch = itUserMatch.next();
            List<Integer> userMatchFractions = CollUtil.newArrayList((userMatch.getUserStyle()).getThought(),
                    (userMatch.getUserStyle()).getSpend(), (userMatch.getUserStyle()).getNature(),
                    (userMatch.getUserStyle()).getDiscipline(), (userMatch.getUserStyle()).getThinking());

            Iterator<Integer> itSF = styleFractions.iterator();
            Iterator<Integer> itUMF = userMatchFractions.iterator();
            float similarity = 0; //性格指数差距
            while (itSF.hasNext() && itUMF.hasNext()) {
                similarity += Math.abs(itSF.next() - itUMF.next());
            }
            //相识度计算 总差距/比较数量=平均每个指数差距 ，（最大差距-实际每个指数差距）/最大差距=相似度
            float lastSimilarity = (float) (BaseConstant.MATCH_SIMILAR - (similarity / BaseConstant.MATCH_NUM)) / BaseConstant.MATCH_SIMILAR;
            userMatch.setSimilarity(lastSimilarity * 100);
        }
//根据创建相似度降序排序
        matchStyles.sort(Comparator.comparing(UserMatch::getSimilarity).reversed());
        return matchStyles;
    }


    /**
     * @Param: [userLoginAppInfo]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/26 16:56
     * 用户登入
     */
    @Override
    public ResponseJson userLoginByAppInfo(UserLoginAppInfo userLoginAppInfo) {
        List<UserLogin> userLogins = hasLoginByLoginName(userLoginAppInfo.getLoginName());
        //当用户登入账号不存在时
        if (CollUtil.isEmpty(userLogins)) {
            //        loginMethod 为false时为非第三方
            if (!userLoginAppInfo.getLoginMethod()) {
                responseJson.setResPonseSelfMsg("账号不存在，请注册或采用第三方登入");
                return responseJson;
            }
//            添加登入信息
            String loginId = IdUtil.simpleUUID();
            int userLoginFalg = insertUserLogin(userLoginAppInfo, loginId);
            if (userLoginFalg <= 0) {
                responseJson.setResPonseSelfMsg("登入失败，请重试");
                return responseJson;
            }
//            添加简要的用户信息
            UserInfo userInfo = insertUserInfo(userLoginAppInfo, loginId);
            if (userInfo == null) {
                responseJson.setResPonseSelfMsg("用户信息录入失败，检查网络");
                return responseJson;
            }
            responseJson.setSuccessResPonse(userInfo);
            return responseJson;
        }
//        不是第三方时判断登入账号密码是否正确

        if (!userLoginAppInfo.getLoginMethod()) {
            String pwd=SecureUtil.md5(userLoginAppInfo.getPassword());
            if (!(userLoginAppInfo.getLoginName().equals(userLogins.get(0).getLoginName()) &&
                    userLogins.get(0).getPassword().equals(pwd))) {
                responseJson.setResPonseSelfMsg("用户名/密码不正确");
                return responseJson;
            }
        }

        UserInfo userInfo = userInfoMapper.getUserInfoByLoginId(userLoginAppInfo.getLoginName());

      //登入成功时，返回用户信息
        responseJson.setSuccessResPonse(userInfo);
        return responseJson;
    }

    /**
     * @Param: [userLoginAppInfo]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/10/27 0:58
     * 用户注册
     */
    @Override
    public ResponseJson userRegisteredByAppInfo(UserLoginAppInfo userLoginAppInfo) {
        List<UserLogin> userLogins = hasLoginByLoginName(userLoginAppInfo.getLoginName());
        //当用户登入账号存在时
        if (!CollUtil.isEmpty(userLogins)) {
            responseJson.setResPonseSelfMsg("该账号已存在，换个账号试试");
            return responseJson;
        }
        //            添加登入信息
        String loginId = IdUtil.simpleUUID();
        int userLoginFalg = insertUserLogin(userLoginAppInfo, loginId);
        if (userLoginFalg <= 0) {
            responseJson.setResPonseSelfMsg("注册失败，请重试");
            return responseJson;
        }
        //            添加简要的用户信息
        UserInfo userInfo = insertUserInfo(userLoginAppInfo, loginId);
        if (userInfo == null) {
            responseJson.setResPonseSelfMsg("用户信息录入失败，检查网络");
            return responseJson;
        }
        responseJson.setResPonseSelfMsg("注册成功");
        return responseJson;
    }

    /**
     * @Param: [loginName]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/26 17:07
     * 判断是否存在该账号
     */
    public List<UserLogin> hasLoginByLoginName(String loginName) {
        UserLoginExample example = new UserLoginExample();
        UserLoginExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<UserLogin> userLogins = userLoginMapper.selectByExample(example);
        return userLogins;
    }

    /**
     * @Param: [userLoginAppInfo]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/10/26 23:37
     * 用户登入表记录添加
     */
    public Integer insertUserLogin(UserLoginAppInfo userLoginAppInfo, String loginId) {
        UserTypeExample userTypeExample = new UserTypeExample();
        UserTypeExample.Criteria userTypeExampleCriteria = userTypeExample.createCriteria();
        userTypeExampleCriteria.andUserTypeNameEqualTo(userLoginAppInfo.getUserTypeName());
        List<UserType> userTypes = userTypeMapper.selectByExample(userTypeExample);
        if (CollUtil.isEmpty(userTypes)) {
            return 0;
        }
        String userTypeId = userTypes.get(0).getId();
        UserLogin userLogin = new UserLogin();
        userLogin.setId(loginId);
        userLogin.setUserTypeId(userTypeId);
        userLogin.setLoginName(userLoginAppInfo.getLoginName());
        if (userLoginAppInfo.getPassword() != null || !("".equals(userLoginAppInfo.getPassword()))) {
             String pwd= SecureUtil.md5(userLoginAppInfo.getPassword());
            userLogin.setPassword(pwd);
        }
        userLogin.setIsState(BaseConstant.STATE_NORMAL);
        userLogin.setGmtCreate(DateUtil.now());
        userLogin.setGmtModified(DateUtil.now());
        int loginFalg = userLoginMapper.insertSelective(userLogin);
        if (loginFalg <= 0) {
            return 0;
        }
        return 1;
    }

    /**
     * @Param: []
     * @Return: void
     * @Author: lcf
     * @Date: 2019/10/26 23:46
     * 添加用户信息表简要信息
     */
    public UserInfo insertUserInfo(UserLoginAppInfo userLoginAppInfo, String loginId) {
        String userInfoId = IdUtil.simpleUUID();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoId);
        userInfo.setUserLoginId(loginId);
        userInfo.setUserName(userLoginAppInfo.getUserName());
        if (userLoginAppInfo.getAvatar() != null || !("".equals(userLoginAppInfo.getAvatar()))) {
            userInfo.setAvatar(userLoginAppInfo.getAvatar());
        }
        userInfo.setAvatar(BaseConstant.AVATAR_NORMAL);
        userInfo.setHasRealName(BaseConstant.REAL_NAME);
        userInfo.setGmtCreate(DateUtil.now());
        userInfo.setGmtModified(DateUtil.now());
        int insertUserInfoFlag = userInfoMapper.insertSelective(userInfo);
        if (insertUserInfoFlag <= 0) {
            return null;
        }
        return userInfo;
    }
}
