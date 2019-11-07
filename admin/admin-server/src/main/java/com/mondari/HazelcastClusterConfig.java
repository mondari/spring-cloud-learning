package com.mondari;

import com.hazelcast.config.*;
import com.hazelcast.map.merge.PutIfAbsentMapMergePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.singletonList;

/**
 * @Author: lmd
 * @Description: Hazelcast 配置 admin 集群类
 * @Date: 2019/11/7 10:32
 * @Version: 1.0
 */
@Configuration
public class HazelcastClusterConfig {
    @Bean
    public Config hazelcastConfig() {
        //This map is used to store the events.
        //It should be configured to reliably hold all the data,
        //Spring Boot Admin will compact the events, if there are too many
        MapConfig eventStoreMap = new MapConfig("spring-boot-admin-event-store")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(1)
                .setEvictionPolicy(EvictionPolicy.NONE)
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMapMergePolicy.class.getName(), 100));

        //This map is used to deduplicate the notifications.
        //If data in this map gets lost it should not be a big issue as it will atmost lead to
        //the same notification to be sent by multiple instances
        MapConfig sentNotificationsMap = new MapConfig("spring-boot-admin-sent-notifications")
                .setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(1)
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMapMergePolicy.class.getName(), 100));

        Config config = new Config();
        config.addMapConfig(eventStoreMap);
        config.addMapConfig(sentNotificationsMap);
        config.setProperty("hazelcast.jmx", "true");

        //WARNING: This setups a local cluster, you change it to fit your needs.
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        TcpIpConfig tcpIpConfig = config.getNetworkConfig().getJoin().getTcpIpConfig();
        tcpIpConfig.setEnabled(true);
        // 添加集群成员
        tcpIpConfig.setMembers(singletonList("127.0.0.1"));
        return config;
    }
}
