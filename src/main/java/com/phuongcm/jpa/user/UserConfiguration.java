package com.phuongcm.jpa.user;

import com.phuongcm.jpa.user.cache.PermissionCache;
import com.phuongcm.jpa.user.repository.PermissionRepository;
import com.phuongcm.jpa.user.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@EnableConfigurationProperties(LoginProperties.class)
public class UserConfiguration {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("channel_update_permissions"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PermissionCache permissionCache){
        return new MessageListenerAdapter(permissionCache);
    }

    @Bean
    PermissionCache permissionCache(RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository){
        return new PermissionCache(rolePermissionRepository, permissionRepository);
    }

    @Bean
    @ConditionalOnMissingBean(name = "stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate(final RedisConnectionFactory connectionFactory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }
}
