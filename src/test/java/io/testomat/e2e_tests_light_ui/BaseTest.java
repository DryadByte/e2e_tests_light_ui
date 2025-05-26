package io.testomat.e2e_tests_light_ui;


import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.testomat.e2e_tests_light_ui.config.TestConfig;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;


public abstract class BaseTest {

    protected static TestConfig config;
    protected static TestConfig.Credentials credentials;

    @BeforeAll
    static void setUp() throws IOException {
        // Load YAML configuration
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        config = mapper.readValue(new File("src/test/resources/config.yml"), TestConfig.class);
        credentials = config.getCredentials();
        Configuration.baseUrl = config.getBaseUrl();
    }
}