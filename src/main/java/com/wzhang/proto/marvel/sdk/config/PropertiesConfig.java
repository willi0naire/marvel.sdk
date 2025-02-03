package com.wzhang.proto.marvel.sdk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * configures to read from application.properties
 */
@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesConfig {
}
