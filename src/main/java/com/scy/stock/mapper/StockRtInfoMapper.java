package com.scy.stock.mapper;

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
}
