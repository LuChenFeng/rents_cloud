package pers.lcf.rents;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
//@EnableAutoConfiguration
// @ComponentScan(basePackages = {"pers.lcf.rents"})
@SpringBootApplication
@MapperScan("pers.lcf.rents")
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
