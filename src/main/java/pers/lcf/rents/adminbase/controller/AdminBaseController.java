package pers.lcf.rents.adminbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import pers.lcf.rents.adminbase.model.OrdinaryUser;
import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.utils.ResponseJson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
     * @Param: [ordinaryUserDTO]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/9 14:29
     * 普通用户分页查询
     */
    @PostMapping("/getOrdinaryUsers")
    public ResponseJson getOrdinaryUsersByDTO(@RequestBody OrdinaryUserDTO ordinaryUserDTO) {
        OrdinaryUserDTO dto = adminBaseServiceImpl.getOrdinaryUsersByDTO(ordinaryUserDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }

    /**
     * @Param: [ordinaryUser]
     * @Return: pers.lcf.rents.utils.ResponseJson
     * @Author: lcf
     * @Date: 2019/11/9 14:29
     * 普通用户信息修改
     */
    @PutMapping("/ordinaryUsers")
    public ResponseJson updateOrdinaryUser(@RequestBody OrdinaryUser ordinaryUser) {
        int flag = adminBaseServiceImpl.updateOrdinaryUser(ordinaryUser);
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
}
