package pers.lcf.rents.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @ClassName WebMvcConfig
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/10/5 11:25
 **/
import pers.lcf.rents.utils.BaseConstant;
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
         * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
         * 用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
         */

        registry.addResourceHandler("/rentsUpLoad/**").addResourceLocations("file:"+BaseConstant.IMG_PATH);
        super.addResourceHandlers(registry);
    }

}
