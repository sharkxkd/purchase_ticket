package com.sharkxkd.ticket;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.module.Configuration;

/**
 * 12306系统启动类
 * ServletComponentScan : 帮助访问对应的Controller
 * @author zc
 * @date 2024/11/12 15:51
 **/
@SpringBootApplication
@Slf4j
@ServletComponentScan
@MapperScan(basePackages = {"com.sharkxkd.ticket.dao"})
public class PurchaseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(PurchaseApplication.class, args);
    }
}
