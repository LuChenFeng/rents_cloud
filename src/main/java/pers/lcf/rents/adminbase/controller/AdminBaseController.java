package pers.lcf.rents.adminbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


import pers.lcf.rents.adminbase.model.*;
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

    /**
     * @Param: [ids]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:20
     * 用户信息批量删除
     */
    @DeleteMapping("/ordinaryUsers/{ids}")
    public ResponseJson delOrdinaryUserById(@PathVariable("ids") String ids) {
        List<String> userInfoIds = Arrays.asList(ids.split(","));
        int flag = adminBaseServiceImpl.delOrdinaryUserById(userInfoIds);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: []
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:21
     * 普通用户饼图数据
     */
    @GetMapping("/ordinaryUsersPei")
    public ResponseJson getOrdinaryUsersPei() {
        OrdinaryUsersPei ordinaryUsersPei = adminBaseServiceImpl.getOrdinaryUsersPei();
        responseJson.setSuccessResPonse(ordinaryUsersPei);
        return responseJson;
    }

    /**
     * @Param: [userDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:21
     * 管理员用户分页
     */
    @PostMapping("/adminUsers")
    public ResponseJson getAdminUsersDTO(@RequestBody UserDTO userDTO) {
        UserDTO dto = adminBaseServiceImpl.getAdminUsersDTO(userDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }

    /**
     * @Param: [user]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:21
     * 修改管理员用户信息
     */
    @PutMapping("/adminUsers")
    public ResponseJson updataAdminUsersDTO(@RequestBody User user) {
        int flag = adminBaseServiceImpl.updateOrdinaryUser(user);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [postsReportDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/13 20:51
     * 帖子举报分页查
     */
    @PostMapping("/getPostsReports")
    public ResponseJson getPostsReportsByDTO(@RequestBody PostsReportDTO postsReportDTO) {
        PostsReportDTO dto = adminBaseServiceImpl.getPostsReportsByDTO(postsReportDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }

    /**
     * @Param: [report]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:21
     * 举报记录修改
     */
    @PutMapping("/postsReports")
    public ResponseJson updatePostsReports(@RequestBody Report report) {
        Integer flag = adminBaseServiceImpl.updatePostsReports(report);
        responseJson.setResPonse(flag);
        return responseJson;
    }
    /**
     * @Param: [ids]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 14:26
     * 举报记录批量删除
     */
    @DeleteMapping("/postsReports/{ids}")
    public ResponseJson delPostsReportById(@PathVariable("ids") String ids) {
        List<String> postsReportIds = Arrays.asList(ids.split(","));
        int flag = adminBaseServiceImpl.delPostsReportById(postsReportIds);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [postsReportDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 15:57
     * 用户实名记录分页
     */
    @PostMapping("/getUserRealNames")
    public ResponseJson getUserRealNamesByDTO(@RequestBody UserRealNameDTO userRealNameDTO) {
        UserRealNameDTO dto = adminBaseServiceImpl.getUserRealNamesByDTO(userRealNameDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }

    /**
     * @Param: [userRealName]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 22:36
     * 用户实名审核信息修改
     */
    @PutMapping("/userRealNames")
    public ResponseJson updateUserRealNames(@RequestBody UserRealName userRealName) {
        Integer flag = adminBaseServiceImpl.updateUserRealNames(userRealName);
        responseJson.setResPonse(flag);
        return responseJson;
    }
/**
 * @Param: [ids]
 * @Return: pers.lcf.rents.utils.ResponseJson
 * @Author: lcf
 * @Date: 2019/11/19 11:24
 * 批量删除实名信息
 */
    @DeleteMapping("/userRealNames/{ids}")
    public ResponseJson delUserRealNames(@PathVariable("ids") String ids) {
        List<String> userInfoIds = Arrays.asList(ids.split(","));
        int flag = adminBaseServiceImpl.delUserRealNameById(userInfoIds);
        responseJson.setResPonse(flag);
        return responseJson;
    }

    /**
     * @Param: [postsReportDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/16 15:57
     * 用户实名记录分页
     */
    @PostMapping("/getPostsInfo")
    public ResponseJson getPostsInfoByDTO(@RequestBody PostDetailsDTO postDetailsDTO) {
        PostDetailsDTO dto = adminBaseServiceImpl.getPostsInfoByDTO(postDetailsDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }


}
