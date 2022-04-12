package org.csu.mypetstoreclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.csu.mypetstoreclient.persistence")
public class MypetstoreClientBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypetstoreClientBackApplication.class, args);
    }

}
