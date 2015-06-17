package com.capgemini.poc.microservice2;

import java.net.*;
import java.util.concurrent.*;

import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.MetricRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MetricsConfiguration {
	
    @Autowired
    private MetricRegistry metricRegistry;
    
    @Value("${graphite.host}")
    private String graphiteHost;

    @Value("${graphite.port}")
    private int graphitePort;
    
    @Value("${graphite.key}")
    private String graphiteKey;
    
    @PostConstruct
    public void connectRegistryToGraphite() 
    {
        final Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                                                          .prefixedWith(graphiteKey)
                                                          .convertRatesTo(TimeUnit.SECONDS)
                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                          .filter(MetricFilter.ALL)
                                                          .build(graphite);
        reporter.start(1, TimeUnit.MINUTES);
    }
}