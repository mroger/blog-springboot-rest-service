package br.org.roger.exam.blog.configuration;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * http://infinispan.org/tutorials/simple/spring/
 *
 * https://github.com/infinispan/infinispan-spring-boot
 *
 * http://infinispan.org/tutorials/embedded/
 *
 * Testar: https://mvnrepository.com/artifact/org.infinispan/infinispan-jcache
 *
 * TODO: Implementar server mode com cluster
 */
@Configuration
public class InfinispanConfig {

    private final EmbeddedCacheManager cacheManager;

    @Autowired
    public InfinispanConfig(EmbeddedCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
/*@Configuration
public class InfinispanConfig extends SpringBootServletInitializer implements CachingConfigurer {

    @Override
    public CacheManager cacheManager() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder = new ConfigurationBuilder();
        builder.addServer().host("localhost").port(9999);
        builder.maxRetries(1).socketTimeout(2000).connectionTimeout(3000);
        return new SpringRemoteCacheManager(new RemoteCacheManager(builder.build(),true));
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}*/
