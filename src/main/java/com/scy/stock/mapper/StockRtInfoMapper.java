package com.scy.stock.mapper;

import com.scy.stock.domain.Stock4EvrDayDomain;
import com.scy.stock.domain.Stock4MinuteDomain;
import com.scy.stock.domain.StockUpdownDomain;
import com.scy.stock.pojo.StockRtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author Jack
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.StockRtInfo
*/
@Mapper
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> stockIncreaseLimit(Date curDateTime);

    List<StockUpdownDomain> stockAll();

    List<Map> upDownCount(@Param("avlDate") Date curTime, @Param("openDate") Date openDate, @Param("flag") Integer flag);

    List<Map> stockUpDownScopeCount(@Param("avlDate") Date avlDate);

    List<Stock4MinuteDomain> stockScreenTimeSharing(@Param("stockCode") String code, @Param("startDate") Date avlDate, @Param("endtDate") Date endDate);

    List<Stock4EvrDayDomain> stockCrxeenDkLine(@Param("stockCode") String stockCode, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取指定日期范围内的收盘价格
     * @param code 股票编码
     * @param beginDate 起始时间
     * @param endDate 结束时间
     * @return
     */
    List<Date> getCloseDates(@Param("code") String code, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取指定股票在指定日期下的数据
     * @param code 股票编码
     * @param dates 指定日期集合
     * @return
     */
    List<Stock4EvrDayDomain> getStockCreenDkLineData(@Param("code") String code, @Param("dates") List<Date> dates);

    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(@Param("list") List<StockRtInfo> stockRtInfoList);
}
