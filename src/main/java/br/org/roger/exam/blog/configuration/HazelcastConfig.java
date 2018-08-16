package br.org.roger.exam.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://memorynotfound.com/spring-boot-hazelcast-caching-example-configuration/
 *
 * https://opencredo.com/spring-booting-hazelcast/
 * https://github.com/opencredo/springboot-hazelcast-example/tree/master
 *
 * http://www.javainuse.com/spring/spring-boot-hazelcast
 *
 * Decreasing preference of Spring Boot search for cache implementations:
 * JCache
 * Hazelcast
 * Varnish
 * Infinispan
 * Couchbase
 * Redis
 * Caffeine
 * Guava
 */
//@Configuration
public class HazelcastConfig {

    /*@Bean
    public Config hazelCastConfig(){
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("blogposts")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(20));
    }*/

}