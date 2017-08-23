# twitter-mine
## Environment setup 
1. Download and install [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Set the JAVA_HOME environment variable to the root directory of your Java installation.
  
2. Download and install [Maven 3.3.9](https://maven.apache.org/)
3. Download and install [Elasticsearch 2.4.5](https://www.elastic.co/downloads/past-releases/elasticsearch-2-4-5)
4. Download and install [Kibana 4.6.5](https://www.elastic.co/downloads/past-releases/kibana-4-6-5) (optional)

## Elasticsearch and Kibana
```
cluster.name: twitter-mine
node.master: true
node.data: true
transport.tcp.port: 9300
http.port : 9200
discovery.zen.ping.unicast.hosts: ["localhost:9300", "localhost:9301"]
discovery.zen.minimum_master_nodes: 1
gateway.recover_after_time: 20s
index.unassigned.node_left.delayed_timeout: 20
```

1. Execute {elasticsearch-root}/bin/elasticsearch.bat 

2. Execute {kibana-root}/bin/kibana.bat 

## Running the services

Given that you have an elasticsearch cluster set up properly, you can start the services.

Configure elasticsearch nodes in application.yml

Import the services in your IDE and run the Application classes or go to the root directory of the services and run "mvn spring-boot:run" for each one
