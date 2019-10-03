package pers.lcf.rents.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lcf.rents.forum.model.User;
import pers.lcf.rents.forum.service.IUserService;
import pers.lcf.rents.utils.ResponseJson;


@RestController
@EnableAutoConfiguration
@RequestMapping("/userb")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ResponseJson responseJson;



    @RequestMapping("/findByUserName")
    public ResponseJson findByUserName(String name) {
        User user = userService.findByUserName(name);
        responseJson.setSuccessResPonse(user);
        return responseJson;
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
