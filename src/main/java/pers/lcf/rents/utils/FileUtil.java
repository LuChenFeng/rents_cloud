package pers.lcf.rents.utils;

import cn.hutool.core.collection.CollUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FileUtil
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/4 22:32
 **/
public class FileUtil {
    /**
     * 第一步：判断文件是否为空   true：返回提示为空信息   false：执行第二步
     * 第二步：判断目录是否存在   不存在：创建目录
     * 第三部：通过输出流将文件写入硬盘文件夹并关闭流
     *
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file, String fileSon) {
        String fileName = file.getOriginalFilename();
        String filePath = BaseConstant.IMG_PATH + fileSon;
        File targetFile = new File(filePath);
        //第一步：判断文件是否为空
        if (file.isEmpty()) {
            return fileName + "文件为空";
        }
        //第二步：判断目录是否存在   不存在：创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //第三部：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BaseConstant.IMG_URL + fileSon + fileName;
    }

    /**
     * @Param: [request, fileSon]
     * @Return: java.util.List<java.lang.String>
     * @Author: lcf
     * @Date: 2019/10/30 19:58
     * 多文件上传公用方法
     */
    public static List<String> multipleFilesUpload(HttpServletRequest request, String fileSon) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<String> imageUrl = CollUtil.newArrayList();
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();
            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                String s = FileUtil.uploadFile(entry.getValue(), fileSon);
                imageUrl.add(s);
            }
        }
        return imageUrl;
    }
/**
 * @Param: [path]
 * @Return: boolean
 * @Author: lcf
 * @Date: 2019/12/1 15:49
 * 删除指定文件
 */
   public static boolean delFile(String path) {
        File file=new File(path);
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

}
