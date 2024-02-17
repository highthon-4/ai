package com.hightoon;

import com.hightoon.dto.Ts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HightoonApplication {

    @PostMapping("/test")
    public String test(@RequestBody Ts ts){
        return "Hello world\n" + "id : " + ts.id() + "\npwd : " + ts.pwd();
    }

    public static void main(String[] args) {
        SpringApplication.run(HightoonApplication.class, args);
    }

}
