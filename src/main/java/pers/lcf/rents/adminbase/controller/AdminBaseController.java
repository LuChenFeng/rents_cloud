package pers.lcf.rents.adminbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.lcf.rents.adminbase.model.OrdinaryUserDTO;
import pers.lcf.rents.adminbase.service.AdminBaseService;
import pers.lcf.rents.utils.ResponseJson;

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


    @PostMapping("/getOrdinaryUsers")
    public ResponseJson getOrdinaryUsersByDTO(@RequestBody OrdinaryUserDTO ordinaryUserDTO) {
        OrdinaryUserDTO dto = adminBaseServiceImpl.getOrdinaryUsersByDTO(ordinaryUserDTO);
        responseJson.setSuccessResPonse(dto);
        return responseJson;
    }
}
