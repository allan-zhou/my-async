package me.example.async.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.example.async.service.task.TaskCommonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhoujialiang9
 * @date 2024/2/23 18:22
 **/
@Slf4j
@RestController
public class HelloController {

    @Resource
    TaskCommonService taskCommonService;


    @RequestMapping(value = "/hello")
    public String hello(String str){

        String result = "hello，" + str;

        log.info(result);

        return result;

    }
}
