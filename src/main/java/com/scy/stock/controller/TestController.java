package com.scy.stock.controller;

import com.scy.stock.service.StockTimeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TestController {

    @Autowired
    private StockTimeTaskService stockTimeTaskServiceImpl;

    @GetMapping("/testInnerUrl")
    public void test(){
        stockTimeTaskServiceImpl.getInnerMarketInfo();
    }
}
