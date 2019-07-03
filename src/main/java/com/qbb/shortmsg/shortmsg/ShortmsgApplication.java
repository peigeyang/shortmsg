package com.qbb.shortmsg.shortmsg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.qbb.shortmsg.shortmsg.mapper")
public class ShortmsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortmsgApplication.class, args);
    }

}
