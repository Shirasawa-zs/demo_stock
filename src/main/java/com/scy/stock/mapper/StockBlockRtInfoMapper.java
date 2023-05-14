package com.scy.stock.mapper;

import com.scy.stock.pojo.StockBlockRtInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Jack
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.StockBlockRtInfo
*/
@Mapper
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockRtInfo> sectorAllLimit();
}
