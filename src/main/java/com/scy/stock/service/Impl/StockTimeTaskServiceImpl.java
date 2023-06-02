package com.scy.stock.service.Impl;

import com.google.common.collect.Lists;
import com.scy.stock.common.StockInfoConfig;
import com.scy.stock.config.ParserStockInfoUtil;
import com.scy.stock.mapper.StockBlockRtInfoMapper;
import com.scy.stock.mapper.StockBusinessMapper;
import com.scy.stock.mapper.StockMarketIndexInfoMapper;
import com.scy.stock.mapper.StockRtInfoMapper;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockMarketIndexInfo;
import com.scy.stock.pojo.StockRtInfo;
import com.scy.stock.service.StockTimeTaskService;
import com.scy.stock.utils.DateTimeUtil;
import com.scy.stock.utils.IdWorker;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("stockTimeTaskServiceImpl")
public class StockTimeTaskServiceImpl implements StockTimeTaskService {
    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    /**
     * 批量获取股票分时数据详情信息
     * http://hq.sinajs.cn/list=s_sh000001,s_sz399001
     * 响应数据格式
     * var hq_str_s_sh000001="上证指数,3204.6345,0.0701,0.00,3123307,40098148";
     */
    @Override
    public void getInnerMarketInfo() {
        //组装动态url
        //http://hq.sinajs.cn/list=s_sh000001,s_sz399001
        String innerUrl= stockInfoConfig.getMarketUrl() + String.join(",",stockInfoConfig.getInner());
        //设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        //请求
        String result = restTemplate.postForObject(innerUrl, new HttpEntity<>(headers), String.class);
        String reg="var hq_str_(.+)=\"(.+)\";";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(result);
        //收集大盘封装后的对象
        //请求响应格式var hq_str_s_sh000001="上证指数,3204.6345,0.0701,0.00,3123307,40098148";
        ArrayList<StockMarketIndexInfo> stockMarketIndexInfoArrayList = new ArrayList<>();
        while (matcher.find()){
            //获取大盘id
            String marketCode = matcher.group(1);
            //其他信息封装
            String other = matcher.group(2);
            String[] others = other.split(",");
            //大盘名称
            String marketName = others[0];
            //当前点
            BigDecimal curPoint = new BigDecimal(others[1]);
            //当前价格
            BigDecimal curPrice=new BigDecimal(others[2]);
            //涨跌率
            BigDecimal upDownRate = new BigDecimal(others[3]);
            //成交量
            Long tradeAmount = Long.valueOf(others[4]);
            //成交金额
            Long tradeVol = Long.valueOf(others[5]);
            //当前日期
            Date now = DateTimeUtil.getDateTimeWithoutSecond(DateTime.now()).toDate();
            StockMarketIndexInfo stockMarketIndexInfo = StockMarketIndexInfo.builder()
                                                        .id(idWorker.nextId()+"")
                                                        .markName(marketName)
                                                        .tradeVolume(tradeVol)
                                                        .tradeAccount(tradeAmount)
                                                        .updownRate(upDownRate)
                                                        .curTime(now)
                                                        .curPoint(curPoint)
                                                        .currentPrice(curPrice)
                                                        .markId(marketCode)
                                                        .build();
            stockMarketIndexInfoArrayList.add(stockMarketIndexInfo);
        }
        if(CollectionUtils.isEmpty(stockMarketIndexInfoArrayList)) {
            return;
        }
        String curTime = DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));
        int count = stockMarketIndexInfoMapper.insertBatch(stockMarketIndexInfoArrayList);
    }

    /**
     * 批量获取股票分时数据详情信息
     * http://hq.sinajs.cn/list=sz000002,sh600015
     * 响应数据格式
     * var hq_str_sh601006="大秦铁路, 27.55, 27.25, 26.91, 27.55, 26.20, 26.91, 26.92,22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89, 14300,
     * 26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150, 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
     */
    @Override
    public void getStockRtIndex() {
        //批量获取股票ID集合
        List<String> stockIds = stockBusinessMapper.getStockIds();
        //计算出符合sina命名规范的股票id数据
        stockIds = stockIds.stream().map(id -> {
            return id.startsWith("6") ? "sh" + id : "sz" + id;
        }).collect(Collectors.toList());
        //设置请求头数据
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Lists.partition(stockIds,20).forEach(list -> {
            //拼接股票url地址
            String stockUrl = stockInfoConfig.getMarketUrl() + String.join(",",list);
            //获取响应数据
            String result = restTemplate.postForObject(stockUrl, entity, String.class);
            List<StockRtInfo> infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, 3);
            stockRtInfoMapper.insertBatch(infos);
        });
    }


    /**
     * 批量获取股票分时数据详情信息
     * http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php
     * 响应数据格式
     * var S_Finance_bankuai_sinaindustry = {
     * "new_blhy":"new_blhy,玻璃行业,19,19.293684210526,-0.17052631578947,-0.87610188740468,315756250,5258253314,sh600586,3.464,9.260,0.310,金晶科技",
     * "new_cbzz":"new_cbzz,船舶制造,8,12.15875,0.0125,0.10291242152928,214866817,2282104956,sh600150,0.978,24.790,0.240,中国船舶",
     */
    @Override
    public void getStockSectorRtIndex() {
        //发送板块数据请求
        String result = restTemplate.getForObject(stockInfoConfig.getBlockUrl(), String.class);
        //响应结果转板块集合数据
        List<StockBlockRtInfo> infos = parserStockInfoUtil.parse4StockBlock(result);
        //数据分片保存到数据库下 行业板块类目大概50个，可每小时查询一次即可
        Lists.partition(infos,20).forEach(list->{
            //20个一组，批量插入
            stockBlockRtInfoMapper.insertBatch(list);
        });
    }
}
