package com.scy.stock.service;

import com.scy.stock.pojo.StockBusiness;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockService {
    /**
     * 获取所有股票信息
     * @return
     */
    List<StockBusiness> getAllStockBusiness();
}