package com.scy.stock.mapper;

import com.scy.stock.pojo.StockBusiness;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Jack
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.StockBusiness
*/
@Mapper
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<StockBusiness> getAll();

    /**
     * 获取所有股票的code
     * @return
     */
    List<String> getStockIds();
}
