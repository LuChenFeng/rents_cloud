package pers.lcf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


/**
 * Hello world!
 *
 */
//@EnableAutoConfiguration
// @ComponentScan(basePackages = {"pers.lcf.rents"})
@SpringBootApplication
@MapperScan("pers.lcf.rents")
@Configuration
public class BootRentsApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(BootRentsApplication.class,args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("5MB"));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("50MB"));
        return factory.createMultipartConfig();
    }
}
