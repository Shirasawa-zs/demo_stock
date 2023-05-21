package com.scy.stock.controller;

import com.scy.stock.domain.InnerMarketDomain;
import com.scy.stock.domain.StockBlockRtInfoDomain;
import com.scy.stock.domain.StockUpdownDomain;
import com.scy.stock.mapper.StockRtInfoMapper;
import com.scy.stock.pojo.StockBlockRtInfo;
import com.scy.stock.pojo.StockBusiness;
import com.scy.stock.service.StockService;
import com.scy.stock.utils.PageResult;
import com.scy.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quot")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService stockService;


    @GetMapping("/stock/business/all")
    public List<StockBusiness> getAllBusiness(){
        return stockService.getAllStockBusiness();
    }

    /**
     *需求说明: 获取最新国内大盘信息
     * @Return
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.innerIndexAll();
    }

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockRtInfoDomain>> sectorAll(){
        return stockService.sectorAllLimit();
    }

    /**
     * 沪深两市个股涨幅分时行情数据查询，以时间顺序和涨幅查询前10条数据
     * @return
     */
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> stockIncreaseLimit(){
        return stockService.stockIncreaseLimit();
    }

    /**
     * 沪深两市个股行情列表查询 ,以时间顺序和涨幅分页查询
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> stockPage(Integer page, Integer pageSize){
        return stockService.stockPage(page, pageSize);
    }

    /**
     * 功能描述：沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     * 		查询每分钟的涨停和跌停的数据的同级；
     * 		如果不在股票的交易日内，那么就统计最近的股票交易下的数据
     * 	 map:
     * 	    upList:涨停数据统计
     * 	    downList:跌停数据统计
     * @return
     */
    @GetMapping("/stock/updown/count")
    public R<Map> upDownCount(){
        return stockService.upDownCount();
    }


    /**
     * @param
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) throws UnsupportedEncodingException {
        stockService.stockExport(response,page,pageSize);
    }
}