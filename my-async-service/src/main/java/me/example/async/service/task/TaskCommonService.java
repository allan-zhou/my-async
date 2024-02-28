package me.example.async.service.task;

import lombok.extern.slf4j.Slf4j;
import me.example.async.service.dto.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * @author zhoujialiang9
 * @date 2024/2/27 10:43
 **/
@Slf4j
@Service
public class TaskCommonService{

    public String doTask(TaskRequest taskRequest) {

        try {

            log.info("开始执行，任务={}", taskRequest.getTaskName());

            long milliseconds = taskRequest.getExeSeconds() * 1000;

            Thread.sleep(milliseconds);

            log.info("完成，任务={}", taskRequest.getTaskName());

            return taskRequest.getDefaultResult();

        } catch (Exception e) {

            return taskRequest.getDefaultResult();
        }

    }
}
