package com.example.computerstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
//MapperScan注解指定当前项目中的Mapper接口路径，在项目启动时会自动加载所有的接口
@MapperScan("com.example.computerstore.mapper")
public class ComputerStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComputerStoreApplication.class, args);
    }
}
