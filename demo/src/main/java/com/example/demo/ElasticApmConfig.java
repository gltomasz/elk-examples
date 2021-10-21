package com.example.demo;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Configuration
@ConfigurationProperties(prefix = "elastic.apm")
@ConditionalOnProperty(value = "elastic.apm.enabled", havingValue = "true")
public class ElasticApmConfig {

    private String serverUrls;
    private String serviceName;
    private String secretToken;
    private String environment;

    @PostConstruct
    public void init() {
        requireNonNull(serverUrls, "elastic.apm.serverUrls property is not set");
        requireNonNull(serviceName, "elastic.apm.serviceName property is not set");
        requireNonNull(secretToken, "elastic.apm.secretToken property is not set");
        requireNonNull(environment, "elastic.apm.environment property is not set");

        Map<String, String> apmProps = Map.of(
                "server_urls", serverUrls,
                "service_name", serviceName,
                "secret_token", secretToken,
                "environment", environment,
                "enable_log_correlation", "true",
                "application_packages", DemoApplication.class.getPackageName());
        ElasticApmAttacher.attach(apmProps);
    }

    public void setServerUrls(String serverUrls) {
        this.serverUrls = serverUrls;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

}