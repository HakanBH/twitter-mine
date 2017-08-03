package com.fmi.mine.twitter.config;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Configuration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String elasticNodes;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    public String getElasticNodes() {
        return elasticNodes;
    }

    public void setElasticNodes(String elasticNodes) {
        this.elasticNodes = elasticNodes;
    }

    public Long getReplicas() {
        String[] split = getElasticNodes().split(",");
        long nodes = Arrays.asList(split).stream().filter(s -> !Strings.isBlank(s)).count();
        return Math.max(nodes / 2, 1);
    }

    @Bean
    public Client client() {
        Client client = null;
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();

        try {
            Integer index = 0;
            String[] clusterHosts = StringUtils.isEmpty(elasticNodes) ? new String[]{} : elasticNodes.split(",");

            InetSocketTransportAddress[] clusters = new InetSocketTransportAddress[clusterHosts.length];
            for (String host : clusterHosts) {
                String[] hostParts = StringUtils.split(host, ":");
                if (hostParts == null) { throw new IllegalArgumentException("Invalid host:port setting: " + host); }

                final int port = Integer.valueOf(hostParts[1]);
                InetSocketTransportAddress transportAddress =
                        new InetSocketTransportAddress(InetAddress.getByName(hostParts[0]), port);
                clusters[index] = transportAddress;
                index++;
            }

            client = TransportClient.builder().settings(settings).build().addTransportAddresses(clusters);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }
}
