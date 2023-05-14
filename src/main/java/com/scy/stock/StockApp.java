package com.scy.stock;

import com.scy.stock.common.StockInfoConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.scy.stock.mapper")
@EnableConfigurationProperties(StockInfoConfig.class)
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }
}
