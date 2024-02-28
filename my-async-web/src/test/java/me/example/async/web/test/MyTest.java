package me.example.async.web.test;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import me.example.async.common.ThreadPoolConfiguration;
import me.example.async.service.dto.TaskRequest;
import me.example.async.service.task.TaskCommonService;
import me.example.async.web.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.*;


/**
 *
 * 并行计算
 * 示例：4个task任务，分别耗时1s、2s、3s
 *
 * 方式一：串行调用。总耗时6s
 * 方式二：线程池，并行调用。总耗时3s，取决于耗时最长的任务。
 * 方式三：配置化线程池，并行调用。
 *
 * @author zhoujialiang9
 * @date 2024/2/27 11:09
 **/
@Slf4j
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MyTest {

    @Resource
    TaskCommonService taskCommonService;

    @Test
    public void test1(){

        Date startTime = new Date();
        log.info("开始。time={}", DateUtil.formatDateTime(startTime));

        String ret1 = taskCommonService.doTask(TaskRequest.builder().exeSeconds(1L).taskName("task_1").defaultResult("result_1").build());
        String ret2 = taskCommonService.doTask(TaskRequest.builder().exeSeconds(2L).taskName("task_2").defaultResult("result_2").build());
        String ret3 = taskCommonService.doTask(TaskRequest.builder().exeSeconds(3L).taskName("task_3").defaultResult("result_3").build());

        Date endTime = new Date();
        log.info("任务结果。任务1={}，任务2={}，任务3={}", ret1, ret2, ret3);
        log.info("结束。time={}，总耗时={}s", DateUtil.formatDateTime(endTime), DateUtil.between(startTime, endTime, DateUnit.SECOND));
    }


    @Test
    public void test2(){

        try {
            Date startTime = new Date();
            log.info("开始。time={}", DateUtil.formatDateTime(startTime));

            ExecutorService threadPool = new ThreadPoolExecutor(4, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

            Future<String> ret1 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(1L).taskName("task_1").defaultResult("result_1").build()));
            Future<String> ret2 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(2L).taskName("task_2").defaultResult("result_2").build()));
            Future<String> ret3 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(3L).taskName("task_3").defaultResult("result_3").build()));

            log.info("任务结果。任务1={}，任务2={}，任务3={}", ret1.get(), ret2.get(), ret3.get());
            Date endTime = new Date();
            log.info("结束。time={}，总耗时={}s", DateUtil.formatDateTime(endTime), DateUtil.between(startTime, endTime, DateUnit.SECOND));

        } catch (Exception e) {
            log.error("error", e);
        }
    }


    @Test
    public void test3(){

        try {
            Date startTime = new Date();
            log.info("开始。time={}", DateUtil.formatDateTime(startTime));

            ExecutorService threadPool = ThreadPoolConfiguration.THREAD_POOLS.get("mytest1");
            Future<String> ret1 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(1L).taskName("task_1").defaultResult("result_1").build()));
            Future<String> ret2 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(2L).taskName("task_2").defaultResult("result_2").build()));
            Future<String> ret3 = threadPool.submit(() -> taskCommonService.doTask(TaskRequest.builder().exeSeconds(3L).taskName("task_3").defaultResult("result_3").build()));

            log.info("任务结果。任务1={}，任务2={}，任务3={}", ret1.get(), ret2.get(), ret3.get());
            Date endTime = new Date();
            log.info("结束。time={}，总耗时={}s", DateUtil.formatDateTime(endTime), DateUtil.between(startTime, endTime, DateUnit.SECOND));

        } catch (Exception e) {
            log.error("error", e);
        }

    }
}

