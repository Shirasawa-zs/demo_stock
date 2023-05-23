package com.scy.stock.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.scy.stock.common.StockInfoConfig;
import com.scy.stock.domain.*;
import com.scy.stock.mapper.StockBlockRtInfoMapper;
import com.scy.stock.mapper.StockBusinessMapper;
import com.scy.stock.mapper.StockMarketIndexInfoMapper;
import com.scy.stock.mapper.StockRtInfoMapper;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.pojo.StockMarketIndexInfo;
import com.scy.stock.service.StockService;
import com.scy.stock.utils.DateTimeUtil;
import com.scy.stock.utils.PageResult;
import com.scy.stock.vo.resp.R;
import com.scy.stock.vo.resp.ResponseCode;
import com.sun.deploy.net.URLEncoder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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
    public R<List<StockBlockRtInfoDomain>> sectorAllLimit() {
        //1.调用mapper接口获取数据
        // TODO 优化 避免全表查询 根据时间范围查询，提高查询效率
        List<StockBlockRtInfoDomain> infos = stockBlockRtInfoMapper.sectorAllLimit();
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    @Override
    public R<List<StockUpdownDomain>> stockIncreaseLimit() {
        //1.直接调用mapper查询前10的数据 TODO 以时间顺序取前10
        //优化：
        //获取当前最近有效时间
        Date curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //mock数据
        String mockStr="2021-12-27 09:47:00";
        curDateTime=DateTime.parse(mockStr,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<StockUpdownDomain> infos = stockRtInfoMapper.stockIncreaseLimit(curDateTime);
        //2.判断是否有数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    @Override
    public R<PageResult<StockUpdownDomain>> stockPage(Integer page, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(page,pageSize);
        //2.通过mapper查询
        List<StockUpdownDomain> infos= stockRtInfoMapper.stockAll();
        if (CollectionUtils.isEmpty(infos)) {
            return R.error("暂无数据");
        }
        //3.封装到PageResult下
        //3.1 封装PageInfo对象
        PageInfo<StockUpdownDomain> listPageInfo = new PageInfo<StockUpdownDomain>(infos);
        //3.2 将PageInfo转PageResult
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(listPageInfo);
        //4.封装R响应对象
        return R.ok(pageResult);
    }

    @Override
    public R<Map> upDownCount() {
        //1.获取股票最近的有效交易日期,精确到秒
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //当前最近有效期
        Date curTime = curDateTime.toDate();
        //开盘日期
        Date openTime = DateTimeUtil.getOpenDate(curDateTime).toDate();
        //TODO mock_data 后续数据实时获取时，注释掉
        String curTimeStr="20220106142500";
        //对应开盘日期 mock_data
        String openTimeStr="20220106092500";
        curTime = DateTime.parse(curTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        openTime = DateTime.parse(openTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //2.统计涨停的数据 约定：1代表涨停 0：跌停
        List<Map> upCount= stockRtInfoMapper.upDownCount(curTime,openTime,1);
        //3.统计跌停的数据
        List<Map> downCount= stockRtInfoMapper.upDownCount(curTime,openTime,0);
        //4.组装数据到map
        HashMap<String, List<Map>> info = new HashMap<>();
        info.put("upList",upCount);
        info.put("downList",downCount);
        //5.响应
        return R.ok(info);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize){
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //分页查询数据
            PageHelper.startPage(page,pageSize);
            List<StockUpdownDomain> infos = this.stockRtInfoMapper.stockAll();
            Gson gson = new Gson();
            List<StockExcelDomain> excelDomains = infos.stream().map(info -> {
                StockExcelDomain domain = new StockExcelDomain();
                BeanUtils.copyProperties(info,domain);
                return domain;
            }).collect(Collectors.toList());
            EasyExcel.write(response.getOutputStream(),StockExcelDomain.class).sheet("股票数据").doWrite(excelDomains);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public R<Map> stockTradeVol4InnerMarket() {
        //1.处理相关数据
        //获取当前T日最近有效时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //获取T日开盘时间
        DateTime openDateTime = DateTimeUtil.getOpenDate(curDateTime);
        //转化为Date类型
        Date curDate = curDateTime.toDate();
        Date openDate = openDateTime.toDate();
        // TODO mock数据模拟
        String tDateStr = "20220103143000";
        curDate = DateTime.parse(tDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr="20220103093000";
        openDate = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //T-1交易日的处理，类似
        DateTime preDateTime = DateTimeUtil.getPreDateTime(curDateTime);
        DateTime preOpenDateTime = DateTimeUtil.getOpenDate(preDateTime);
        Date preDate = preDateTime.toDate();
        Date preOpenDate = preOpenDateTime.toDate();
        //TODO 后续注释掉 mock-data
        String preTStr="20220102143000";
        preDate = DateTime.parse(preTStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //mock-data
        String openDateStr2="20220102093000";
        preOpenDate = DateTime.parse(openDateStr2, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //2.获取T日的股票大盘交易量统计数据
        List<Map> stockMarkedIndexInfoListForCurTime = stockMarketIndexInfoMapper.stockTradeVolCount(stockInfoConfig.getInner(), curDate, openDate);
        List<Map> stockMarkedIndexInfoListForPreTime = stockMarketIndexInfoMapper.stockTradeVolCount(stockInfoConfig.getInner(), preDate, preOpenDate);
        HashMap<String, List<Map>> data = new HashMap<>();
        data.put("volList", stockMarkedIndexInfoListForCurTime);
        data.put("yesVolList",stockMarkedIndexInfoListForPreTime);
        return R.ok(data);
    }

    @Override
    public R<Map> stockUpDownScopeCount() {
        //1.获取当前时间下最近的一个股票交易时间 精确到秒
        DateTime avlDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date avlDate = avlDateTime.toDate();
        //TODO 后续删除 mock-data
        String  mockDate="20220106095500";
        avlDate = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //2.查询
        List<Map> maps = null;
        //获取股票涨幅区间集合
        List<String> upDownRange = null;
        //将List集合下的字符串映射成Map对象
        maps = stockRtInfoMapper.stockUpDownScopeCount(avlDate);
        //获取去股票涨幅区间的集合
        upDownRange = stockInfoConfig.getUpDownRange();
        //将list集合下的字符串映射成Map对象
        List<Map> finalMaps = maps;
        List<Map> orderMap = upDownRange.stream().map(key -> {
            Optional<Map> title = finalMaps.stream().filter(map -> key.equals(map.get("title"))).findFirst();
            //判断对应的map是否存在
            Map tmp = null;
            if (title.isPresent()) {
                tmp = title.get();
            } else {
                tmp = new HashMap();
                tmp.put("title", key);
                tmp.put("count", 0);
            }
            return tmp;
        }).collect(Collectors.toList());
        //3.组装data
        HashMap<String, Object> data = new HashMap<>();
        data.put("time",avlDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        data.put("infos",orderMap);
        //返回响应数据
        return R.ok(data);
    }

    @Override
    public R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code) {
        //1.获取最近有效的股票交易时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //获取当前日期
        Date curDate = curDateTime.toDate();
        //获取当前日期对应的开盘日期
        Date openDate = DateTimeUtil.getOpenDate(curDateTime).toDate();
        //TODO 后续删除 mock-data
        String mockDate="20220106142500";
        curDate=DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr="20220106093000";
        openDate=DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        List<Stock4MinuteDomain> maps= stockRtInfoMapper.stockScreenTimeSharing(code,openDate,curDate);
        //响应前端
        return R.ok(maps);
    }

    /**
     * 功能描述：单个个股日K数据查询 ，可以根据时间区间查询数日的K线数据
     * 		默认查询历史20天的数据；
     * @param code 股票编码
     * @return
     */
    @Override
    public R<List<Stock4EvrDayDomain>> stockCreenDkLine(String code) {
        //获取当前日期前推20天
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //当前时间节点
        Date curTime = curDateTime.toDate();
        //前推20
        Date pre20Day = curDateTime.minusDays(20).toDate();

        //TODO 后续删除
        String avlDate="20220106142500";
        curTime=DateTime.parse(avlDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDate="20220101093000";
        pre20Day=DateTime.parse(openDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        // List<Stock4EvrDayDomain> infos = stockRtInfoMapper.stockCreenDkLine(code, pre20Day, curTime);
        //获取指定范围的收盘日期集合
        List<Date> closeDates=stockRtInfoMapper.getCloseDates(code,pre20Day,curTime);
        //根据收盘日期精准查询，如果不在收盘日期，则查询最新数据
        List<Stock4EvrDayDomain> infos= stockRtInfoMapper.getStockCreenDkLineData(code,closeDates);
        return R.ok(infos);
    }
}