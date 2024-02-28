package me.example.async.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhoujialiang9
 * @date 2024/2/26 18:14
 **/
@Getter
@Setter
@Builder
public class TaskRequest {
    public String taskName;

    public Long exeSeconds;

    public String defaultResult;
}
