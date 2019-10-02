package pers.lcf.rents.forum.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lcf.rents.forum.model.User;
import pers.lcf.rents.forum.service.IUserService;


@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/findByUserName")
    public User findByUserName(String name) {
        User user = userService.findByUserName(name);
        return user;
    }

    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable() Integer id) {
        User user = new User("lcf", "123");
        user.setId(1);
        return user;
    }

//    @RequestMapping("/insertUser")
//    public void inserUser(User user) {
//        userService.insertUser(user);
//    }
}
