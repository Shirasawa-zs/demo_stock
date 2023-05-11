package com.scy.stock.mapper;

import com.scy.stock.pojo.StockMarketLogPrice;

/**
* @author Jack
* @description 针对表【stock_market_log_price(股票大盘 开盘价与前收盘价流水表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.StockMarketLogPrice
*/
public interface StockMarketLogPriceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketLogPrice record);

    int insertSelective(StockMarketLogPrice record);

    StockMarketLogPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketLogPrice record);

    int updateByPrimaryKey(StockMarketLogPrice record);

}
