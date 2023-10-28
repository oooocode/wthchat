package com.wth.chat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhongzb
 * @date 2021/05/27
 */
@SpringBootApplication(scanBasePackages = {"com.wth.chat"})
@MapperScan({"com.wth.chat.common.**.mapper"})
public class MallchatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallchatCustomApplication.class,args);
    }

}