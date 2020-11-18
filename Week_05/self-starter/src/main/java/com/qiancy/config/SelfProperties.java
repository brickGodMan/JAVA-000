package com.qiancy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/11/16
 * @since 1.0.0
 */
@ConfigurationProperties(SelfProperties.PREFIX)
public class SelfProperties {
    public static final String PREFIX = "self";

}
