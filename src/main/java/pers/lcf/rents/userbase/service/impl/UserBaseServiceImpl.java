package pers.lcf.rents.userbase.service.impl;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.userbase.mapper.UserStyleMapper;
import pers.lcf.rents.userbase.model.UserStyle;
import pers.lcf.rents.userbase.service.UserBaseService;

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

    @Override
    public Integer insertUserStyle(UserStyle userStyle) {
        //hutool生成id
        String id = IdUtil.simpleUUID();
        userStyle.setId(id);
        int flag = userStyleMapper.insert(userStyle);
        return flag;
    }
}
