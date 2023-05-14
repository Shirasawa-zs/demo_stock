package com.scy.stock.mapper;

import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author Jack
* @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.StockMarketIndexInfo
*/
@Mapper
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据注定的id集合和日期查询大盘数据
     * @param Ids 大盘id集合
     * @param lDate 对应日期
     * @return
     */
    List<InnerMarketDomain> selectByIdsAndDate(@Param("ids") List<String> Ids, @Param("lastDate") Date lDate);

    List<StockBlockRtInfo> sectorAllLimit();
}
