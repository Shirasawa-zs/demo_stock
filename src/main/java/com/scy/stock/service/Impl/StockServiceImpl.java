package com.scy.stock.service.Impl;

import com.scy.stock.mapper.StockBusinessMapper;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Override
    public List<StockBusiness> getAllStockBusiness() {
        return stockBusinessMapper.getAll();
    }
}