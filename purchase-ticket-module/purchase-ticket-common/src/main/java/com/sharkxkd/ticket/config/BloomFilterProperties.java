package com.sharkxkd.ticket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置类
 *
 * @author zc
 * @date 2024/11/20 16:20
 **/
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "bf")
public class BloomFilterProperties {
    /**
     * 预期插入量
     */
    private Long expectedInsertions;
    /**
     * 误判率
     */
    private Double fpp;
}
