package pers.lcf.rents.forum.controller;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pers.lcf.rents.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


@RestController
@EnableAutoConfiguration
@RequestMapping("/userb")
public class UserController {

    //处理文件上传
    @RequestMapping(value="/uploadimg", method = RequestMethod.POST)
    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String info = null;
        try {
            info = FileUtil.uploadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
    //处理多文件上传
    @RequestMapping(value="/testuploadimgs", method = RequestMethod.POST)
    @ResponseBody
    public String multipleFilesUpload(HttpServletRequest request) {
        //获取上传的文件数组
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //遍历处理文件
        String info = null;
        for (MultipartFile file : files) {
            try {
                String s = FileUtil.uploadFile(file);
                info = info + "-" + s;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }
}
