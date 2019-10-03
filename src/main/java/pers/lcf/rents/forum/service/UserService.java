package pers.lcf.rents.forum.service;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.forum.mapper.UserMapper;
import pers.lcf.rents.forum.model.User;

import java.util.logging.Logger;


@Service
//@Transactional
public class UserService implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String name) {
//        logger.info("-----------------------"+name);

        String id= IdUtil.simpleUUID();
        Logger logger =Logger.getLogger(String.valueOf(IUserService.class));
        logger.info("这是用糊涂自动生成的id:"+id);

        User user = userMapper.findByUserName(name);

        return user;
    }

//    @Override
//    public void insertUser(User user) {
//        userMapper.insertUser(user);
//    }
}
