package me.example.async.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zhoujialiang9
 * @date 2024/2/27 17:38
 **/
@Slf4j
@Configuration
public class ThreadPoolConfiguration {

    public static Map<String, ExecutorService> THREAD_POOLS = new HashMap<>();

    @Resource
    private ThreadPoolConfigurationProperties threadPoolConfigurationProperties;


    @PostConstruct
    private void init(){

        if (threadPoolConfigurationProperties.instances == null) {
            log.info("没有配置线程池实例");
            return;
        }

        log.info("初始化线程池，实例个数={}", threadPoolConfigurationProperties.instances.size());

        for (int i = 0; i < threadPoolConfigurationProperties.instances.size(); i++) {

            ThreadPoolConfigurationProperties.ThreadPoolInfo threadPoolInfo = threadPoolConfigurationProperties.instances.get(i);

            log.info("第{}个线程池的配置信息：{}", i + 1, JSON.toJSON(threadPoolInfo));

            THREAD_POOLS.put(threadPoolInfo.getName(), createThreadPool(threadPoolInfo));

        }

        log.info("线程池初始化完成");

    }

    private ExecutorService createThreadPool(ThreadPoolConfigurationProperties.ThreadPoolInfo threadPoolInfo){

        int corePoolSize = threadPoolInfo.getCorePoolSize();

        int maximumPoolSize = threadPoolInfo.getMaximumPoolSize();

        long keepAliveTime = threadPoolInfo.getKeepAliveTime();

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, blockingQueue);

    }

}
