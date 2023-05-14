package com.scy.stock.service.Impl;

import com.scy.stock.common.StockInfoConfig;
import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.mapper.StockBlockRtInfoMapper;
import com.scy.stock.mapper.StockBusinessMapper;
import com.scy.stock.mapper.StockMarketIndexInfoMapper;
import com.scy.stock.mapper.StockRtInfoMapper;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.pojo.StockMarketIndexInfo;
import com.scy.stock.service.StockService;
import com.scy.stock.utils.DateTimeUtil;
import com.scy.stock.vo.resp.R;
import com.scy.stock.vo.resp.ResponseCode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Override
    public List<StockBusiness> getAllStockBusiness() {
        return stockBusinessMapper.getAll();
    }

    @Override
    public R<List<InnerMarketDomain>> innerIndexAll() {
        List<String> innerIds = stockInfoConfig.getInner();
        Date lDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO 后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        String mockDate="20211226105600";
        lDate = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //调用mapper查询指定日期下对应的国内大盘数据
        List<InnerMarketDomain> maps = stockMarketIndexInfoMapper.selectByIdsAndDate(innerIds,lDate);
        //组装响应的额数据
        if (CollectionUtils.isEmpty(maps)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(maps);
    }

    @Override
    public R<List<StockBlockRtInfo>> sectorAllLimit() {
        //1.调用mapper接口获取数据
        // TODO 优化 避免全表查询 根据时间范围查询，提高查询效率
        List<StockBlockRtInfo> infos = stockBlockRtInfoMapper.sectorAllLimit();
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }
}