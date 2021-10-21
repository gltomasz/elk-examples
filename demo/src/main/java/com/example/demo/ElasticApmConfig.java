package com.example.demo;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> apmProps = new HashMap<>(6);
        apmProps.put("server_urls", serverUrls);
        apmProps.put("service_name", serviceName);
        apmProps.put("secret_token", secretToken);
        apmProps.put("environment", environment);
        apmProps.put("enable_log_correlation", "true");
        apmProps.put("application_packages", DemoApplication.class.getPackageName());
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