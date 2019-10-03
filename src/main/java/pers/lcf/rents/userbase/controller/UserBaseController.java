package pers.lcf.rents.userbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import pers.lcf.rents.userbase.model.UserStyle;
import pers.lcf.rents.userbase.service.UserBaseService;
import pers.lcf.rents.utils.ResponseJson;

/**
 * @ClassName UserBaseController
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/3 20:47
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserBaseController {
    @Autowired
    private UserBaseService userBaseServiceImpl;

    @Autowired
    private  ResponseJson responseJson;
    @PostMapping("/style")
    public ResponseJson insertUserStyle (@RequestBody UserStyle userStyle){
       int flag= userBaseServiceImpl.insertUserStyle(userStyle);
       responseJson.setResPonse(flag);
       return responseJson;
    }

}
