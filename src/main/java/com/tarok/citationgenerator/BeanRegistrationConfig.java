package com.tarok.citationgenerator;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * サードパーティライブラリをDIで使うためのクラス
 */
@Configuration
public class BeanRegistrationConfig {
    @Bean
    public OkHttpClient okHttp() {
        return new OkHttpClient();
    }
}
