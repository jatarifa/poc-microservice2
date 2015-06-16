package com.capgemini.poc.microservice2;

import java.net.*;
import java.util.concurrent.*;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MetricsConfiguration {
	
    @Autowired
    private MetricRegistry metricRegistry;

    @PostConstruct
    public void connectRegistryToGraphite() 
    {
        final Graphite graphite = new Graphite(new InetSocketAddress("carbon.hostedgraphite.com", 2003));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                                                          .prefixedWith("4060a323-51de-4d3a-9967-465bbc8dc962")
                                                          .convertRatesTo(TimeUnit.SECONDS)
                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                          .filter(MetricFilter.ALL)
                                                          .build(graphite);
        reporter.start(1, TimeUnit.MINUTES);
    }
}