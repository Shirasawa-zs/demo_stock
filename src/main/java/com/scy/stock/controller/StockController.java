package com.scy.stock.controller;

import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quot")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock/business/all")
    public List<StockBusiness> getAllBusiness(){
        return stockService.getAllStockBusiness();
    }
}