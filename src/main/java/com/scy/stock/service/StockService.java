package com.scy.stock.service;

import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.domain.StockBlockRtInfoDomain;
import com.scy.stock.domain.StockUpdownDomain;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.utils.PageResult;
import com.scy.stock.vo.resp.R;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

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

    /**
     * 沪深两市个股涨幅分时行情数据查询，以时间顺序和涨幅查询前10条数据
     * @return
     */
    R<List<StockUpdownDomain>> stockIncreaseLimit();

    /**
     * 沪深两市个股行情列表查询 ,以时间顺序和涨幅分页查询
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    R<PageResult<StockUpdownDomain>> stockPage(Integer page, Integer pageSize);

    R<Map> upDownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize) throws UnsupportedEncodingException;
}