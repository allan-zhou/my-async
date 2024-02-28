package me.example.async.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2024/2/27 15:20
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPoolConfigurationProperties {

    /**
     * 实例
     */
    public List<ThreadPoolInfo> instances;

    @Data
    public static class ThreadPoolInfo {

        private String name;

        private Integer corePoolSize;

        private Integer maximumPoolSize;

        private Long keepAliveTime;
    }
}
