package pers.lcf.rents.userbase.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.userbase.mapper.UserStyleMapper;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserMatch;
import pers.lcf.rents.userbase.model.UserStyle;
import pers.lcf.rents.userbase.model.UserStyleExample;
import pers.lcf.rents.userbase.service.UserBaseService;
import pers.lcf.rents.utils.BaseConstant;

import javax.swing.text.Style;
import java.util.ArrayList;
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
    private UserStyleMapper userStyleMapper;

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
        criteria.andIdEqualTo(userStyle.getId());

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
            float similarity=0; //性格指数差距
            while (itSF.hasNext() && itUMF.hasNext()) {
                similarity+=Math.abs(itSF.next()-itUMF.next());
            }
            //相识度计算 总差距/比较数量=平均每个指数差距 ，（最大差距-实际每个指数差距）/最大差距=相似度
            float lastSimilarity=(float)(BaseConstant.MATCH_SIMILAR-(similarity/BaseConstant.MATCH_NUM))/BaseConstant.MATCH_SIMILAR;
            userMatch.setSimilarity(lastSimilarity*100);
        }
        return matchStyles;
    }

}
