package com.cy.mll_es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @作者 chenyi
 * @date 2019/6/5 16:04
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {
        InetSocketTransportAddress address = new InetSocketTransportAddress(InetAddress.getByName("192.168.1.142"),9300);
        Settings settings = Settings.builder().put("cluster.name").build();
        TransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings);
        preBuiltTransportClient.addTransportAddress(address);
        return preBuiltTransportClient;

    }
}
