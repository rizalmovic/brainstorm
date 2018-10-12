package com.mitrais.brainstorm.persistence;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuration which doesn't extends any of spring boot built-in config.
 */
@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
public class PersistenceConfiguration {
}
