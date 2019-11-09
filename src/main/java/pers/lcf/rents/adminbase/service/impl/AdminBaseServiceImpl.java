package pers.lcf.rents.adminbase.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.userbase.mapper.UserInfoMapper;
import pers.lcf.rents.userbase.mapper.UserLoginMapper;
import pers.lcf.rents.userbase.model.UserInfo;
import pers.lcf.rents.userbase.model.UserInfoExample;
import pers.lcf.rents.userbase.model.UserLogin;
import pers.lcf.rents.userbase.model.UserLoginExample;
import pers.lcf.rents.userbase.service.UserBaseService;

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
    UserBaseService userBaseServiceImpl;
    private int flagUl;
    private int flag;

    /**
     * @Param: [ordinaryUserDTO]
     * @Return: pers.lcf.rents.adminbase.model.OrdinaryUserDTO
     * @Author: lcf
     * @Date: 2019/11/9 14:27
     * 普通用户分页查询
     */
    @Override
    public OrdinaryUserDTO getOrdinaryUsersByDTO(OrdinaryUserDTO ordinaryUserDTO) {
        int[] startEnd = PageUtil.transToStartEnd(ordinaryUserDTO.getPageNo(), ordinaryUserDTO.getPageSize());
        OrdinaryUser user = ordinaryUserDTO.getOrdinaryUsers().get(0);

//        判断时间范围，是否有选中时间
        if (user.getGmtCreateBegin() != null && !("".equals(user.getGmtCreateBegin()))
                && user.getGmtCreateEnd() != null && !("".equals(user.getGmtCreateEnd()))) {
            Date dateBegin = DateUtil.parse(user.getGmtCreateBegin());
            Date dateEnd = DateUtil.parse(user.getGmtCreateEnd());
            if (dateBegin.compareTo(dateEnd) > 0) {
                String date;
                date = user.getGmtCreateBegin();
                user.setGmtCreateBegin(user.getGmtCreateEnd());
                user.setGmtCreateEnd(date);
            }
        } else {
            user.setGmtCreateBegin(null);
            user.setGmtCreateEnd(null);
        }

        List<List<?>> dtoList = userInfoMapper.getOrdinaryUsersByDTO(user, startEnd[0], startEnd[1]);
        List<OrdinaryUser> ordinaryUsers = (List<OrdinaryUser>) dtoList.get(0);
        OrdinaryUserDTO dto = new OrdinaryUserDTO();
        dto.setOrdinaryUsers(ordinaryUsers);
        List<Integer> count = (List<Integer>) dtoList.get(1);
        int totalCount = count.get(0);
        int totalPage = PageUtil.totalPage(totalCount, ordinaryUserDTO.getPageSize());
        dto.setTotalCount(totalCount);
        dto.setTotalPage(totalPage);
        dto.setPageNo(ordinaryUserDTO.getPageNo());
        dto.setPageSize(ordinaryUserDTO.getPageSize());
        return dto;
    }

    /**
     * @Param: [ordinaryUser]
     * @Return: java.lang.Integer
     * @Author: lcf
     * @Date: 2019/11/9 14:26
     * 普通用户信息修改
     */
    @Override
    public Integer updateOrdinaryUser(OrdinaryUser ordinaryUser) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(ordinaryUser, userInfo);    //转换
        int flagUi = userBaseServiceImpl.updataUserInfoById(userInfo);
        if (flagUi <= 0) {
            return flagUi;
        }
        UserLoginExample exampleUl = new UserLoginExample();
        UserLoginExample.Criteria criteriaUl = exampleUl.createCriteria();
        criteriaUl.andLoginNameEqualTo(ordinaryUser.getLoginName());
        UserLogin userLogin = new UserLogin();
        BeanUtils.copyProperties(ordinaryUser, userLogin);
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


}
