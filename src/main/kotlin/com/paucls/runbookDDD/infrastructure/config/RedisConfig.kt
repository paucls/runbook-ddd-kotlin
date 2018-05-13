package com.paucls.runbookDDD.infrastructure.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Configuration for a Redis embedded server for development and integration testing
 */
@Configuration
class RedisConfig {

    private val logger = LoggerFactory.getLogger(RedisConfig::class.java)
    private val hostname = "localhost"
    private val port = 6380
    private val redisServer = redis.embedded.RedisServer(port)

    @PostConstruct
    fun startRedis() {
        logger.info("Starting embedded RedisServer ...")
        this.redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        logger.info("Stopping embedded RedisServer ...")
        this.redisServer.stop()
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration(hostname, port))
    }
}
