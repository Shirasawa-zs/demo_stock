package com.scy.stock.config;

import com.scy.stock.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfiguration {
    /**
     * 配置id生成器
     * @Return
     * */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
