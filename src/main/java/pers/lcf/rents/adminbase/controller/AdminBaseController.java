package pers.lcf.rents.adminbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


import pers.lcf.rents.adminbase.model.PostsReportDTO;
import pers.lcf.rents.adminbase.model.User;
import pers.lcf.rents.adminbase.model.UserDTO;
import pers.lcf.rents.adminbase.model.OrdinaryUsersPei;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.utils.ResponseJson;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName AdminBaseController
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/5 9:25
 **/
@RestController
@EnableAutoConfiguration
@RequestMapping("/admin")
public class AdminBaseController {
    @Autowired
    private ResponseJson responseJson;
    @Autowired
    private AdminBaseService adminBaseServiceImpl;

    /**
     * @Param: [userDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/9 14:29
     * 普通用户分页查询
     */
    @PostMapping("/getOrdinaryUsers")
    public ResponseJson getOrdinaryUsersByDTO(@RequestBody UserDTO userDTO) {
        UserDTO dto = adminBaseServiceImpl.getOrdinaryUsersByDTO(userDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }

    /**
     * @Param: [user]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/9 14:29
     * 普通用户信息修改
     */
    @PutMapping("/ordinaryUsers")
    public ResponseJson updateOrdinaryUser(@RequestBody User user) {
        int flag = adminBaseServiceImpl.updateOrdinaryUser(user);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    @DeleteMapping("/ordinaryUsers/{ids}")
    public ResponseJson delOrdinaryUserById(@PathVariable("ids") String ids) {
        List<String> userInfoIds = Arrays.asList(ids.split(","));
        int flag = adminBaseServiceImpl.delOrdinaryUserById(userInfoIds);
        responseJson.setResPonse(flag);
        return responseJson;
    }
    @GetMapping("/ordinaryUsersPei")
    public  ResponseJson getOrdinaryUsersPei(){
        OrdinaryUsersPei ordinaryUsersPei=  adminBaseServiceImpl.getOrdinaryUsersPei();
        responseJson.setSuccessResPonse(ordinaryUsersPei);
        return responseJson;
    }

    @PostMapping("/adminUsers")
    public  ResponseJson getAdminUsersDTO(@RequestBody UserDTO userDTO){
        UserDTO dto = adminBaseServiceImpl.getAdminUsersDTO(userDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }
    @PutMapping("/adminUsers")
    public  ResponseJson updataAdminUsersDTO(@RequestBody User user){
        int flag = adminBaseServiceImpl.updateOrdinaryUser(user);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    @PostMapping("/getPostsReports")
    public ResponseJson getPostsReportsByDTO(@RequestBody PostsReportDTO postsReportDTO) {
        PostsReportDTO dto = adminBaseServiceImpl.getPostsReportsByDTO(postsReportDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
//        return  null;
    }
}
