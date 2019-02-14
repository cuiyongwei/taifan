package com.education;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@ComponentScan//启动类 所在的包的路径
@MapperScan("com.education.mapper")//springBoot中集成mybates可以让别的类进行引用
@EnableAsync//注解可以异步执行，就是开启多线程的意思在方法上写@Async
@SpringBootApplication(scanBasePackages = "com.education")//：包含@EnableAutoConfiguration、@ComponentScan、@SpringBootConfiguration申明让springboot自动给程序必要的配置
@EnableWebMvc//用于启动springMVC特性
@EnableSwagger2
public class ZhuLei extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ZhuLei.class);
    }//这里继承SpringBootServletInitializer 并重写其中的configure方法目的是使用Spring框架的Servlet3.0支持。并且允许我们可以配置项目从serclet容器中启动

    public static void main(String[] args) {
        SpringApplication.run(ZhuLei.class,args);
    }
}
