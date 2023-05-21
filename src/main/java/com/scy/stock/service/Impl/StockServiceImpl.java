package com.scy.stock.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.scy.stock.common.StockInfoConfig;
import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.domain.StockBlockRtInfoDomain;
import com.scy.stock.domain.StockExcelDomain;
import com.scy.stock.domain.StockUpdownDomain;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}