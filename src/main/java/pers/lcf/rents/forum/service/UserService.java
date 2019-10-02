package pers.lcf.rents.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.lcf.rents.forum.mapper.UserMapper;
import pers.lcf.rents.forum.model.User;


@Service
//@Transactional
public class UserService implements IUserService {


    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String name) {
//        logger.info("-----------------------"+name);

        User user = userMapper.findByUserName(name);

        return user;
    }

//    @Override
//    public void insertUser(User user) {
//        userMapper.insertUser(user);
//    }
}
