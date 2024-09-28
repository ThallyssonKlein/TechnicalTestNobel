package com.nobelcareers.config;

import com.nobelcareers.ports.outbound.observability.MetricCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public MetricCollector metricCollector() {
        return new MetricCollector() {
            @Override
            public void incrementMetric(String metricName) {
                // Não faz nada em ambiente de teste
            }
        };
    }
}