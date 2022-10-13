package com.tarok.citationgenerator.setting;

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
