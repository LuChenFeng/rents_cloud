package pers.lcf.rents.forum.service;


import pers.lcf.rents.forum.model.User;

public interface IUserService {
    public User findByUserName(String name);
//    public  void insertUser(User user);
}
