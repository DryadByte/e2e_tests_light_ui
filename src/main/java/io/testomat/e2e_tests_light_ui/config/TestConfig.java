package io.testomat.e2e_tests_light_ui.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestConfig {
    private Credentials credentials;
    private String baseUrl;
    private String expectedUrl;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credentials {
        private String email;
        private String password;
    }

}
