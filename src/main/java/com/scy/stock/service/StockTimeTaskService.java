package com.scy.stock.service;

/**
 * @author by scy
 * @Date 2023/6/1
 * @Description 定义实时采集股票信息接口
 */
public interface StockTimeTaskService {
    /**
     * 获取国内大盘的实时数据信息
     */
    void getInnerMarketInfo();

    /**
     * 定义获取分钟级股票数据
     */
    void getStockRtIndex();

    /**
     * 获取板块数据
     */
    void getStockSectorRtIndex();
}
