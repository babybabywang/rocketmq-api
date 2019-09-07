package dev.rocketmq;

import dev.rocketmq.producer.DevProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-06 23:41
 * @description 启动类
 */
@SpringBootApplication
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private DevProducer devProducer;

    @GetMapping("send")
    public void send() {
        devProducer.send();
    }
}
