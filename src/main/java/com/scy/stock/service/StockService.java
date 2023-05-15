package com.scy.stock.service;

import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.domain.StockBlockRtInfoDomain;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.vo.resp.R;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockService {
    /**
     *需求说明: 获取所有股票信息
     * @return
     */
    List<StockBusiness> getAllStockBusiness();

    R<List<InnerMarketDomain>> innerIndexAll();

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    R<List<StockBlockRtInfoDomain>> sectorAllLimit();
}