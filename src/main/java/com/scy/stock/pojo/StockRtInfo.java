package com.scy.stock.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 个股详情信息表
 * @TableName stock_rt_info
 */
@Data
public class StockRtInfo implements Serializable {
    /**
     * 主键字段（无业务意义）
     */
    private String id;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 当前时间
     */
    private Date curTime;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 前收盘价| 昨日收盘价
     */
    private BigDecimal preClosePrice;

    /**
     * 当前价格
     */
    private BigDecimal curPrice;

    /**
     * 今日最高价
     */
    private BigDecimal maxPrice;

    /**
     * 今日最低价
     */
    private BigDecimal minPrice;

    /**
     * 成交量
     */
    private Long tradeAmount;

    /**
     * 成交总金额(单位元)
     */
    private BigDecimal tradeVolume;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StockRtInfo other = (StockRtInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getCurTime() == null ? other.getCurTime() == null : this.getCurTime().equals(other.getCurTime()))
            && (this.getStockName() == null ? other.getStockName() == null : this.getStockName().equals(other.getStockName()))
            && (this.getOpenPrice() == null ? other.getOpenPrice() == null : this.getOpenPrice().equals(other.getOpenPrice()))
            && (this.getPreClosePrice() == null ? other.getPreClosePrice() == null : this.getPreClosePrice().equals(other.getPreClosePrice()))
            && (this.getCurPrice() == null ? other.getCurPrice() == null : this.getCurPrice().equals(other.getCurPrice()))
            && (this.getMaxPrice() == null ? other.getMaxPrice() == null : this.getMaxPrice().equals(other.getMaxPrice()))
            && (this.getMinPrice() == null ? other.getMinPrice() == null : this.getMinPrice().equals(other.getMinPrice()))
            && (this.getTradeAmount() == null ? other.getTradeAmount() == null : this.getTradeAmount().equals(other.getTradeAmount()))
            && (this.getTradeVolume() == null ? other.getTradeVolume() == null : this.getTradeVolume().equals(other.getTradeVolume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getCurTime() == null) ? 0 : getCurTime().hashCode());
        result = prime * result + ((getStockName() == null) ? 0 : getStockName().hashCode());
        result = prime * result + ((getOpenPrice() == null) ? 0 : getOpenPrice().hashCode());
        result = prime * result + ((getPreClosePrice() == null) ? 0 : getPreClosePrice().hashCode());
        result = prime * result + ((getCurPrice() == null) ? 0 : getCurPrice().hashCode());
        result = prime * result + ((getMaxPrice() == null) ? 0 : getMaxPrice().hashCode());
        result = prime * result + ((getMinPrice() == null) ? 0 : getMinPrice().hashCode());
        result = prime * result + ((getTradeAmount() == null) ? 0 : getTradeAmount().hashCode());
        result = prime * result + ((getTradeVolume() == null) ? 0 : getTradeVolume().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", stockCode=").append(stockCode);
        sb.append(", curTime=").append(curTime);
        sb.append(", stockName=").append(stockName);
        sb.append(", openPrice=").append(openPrice);
        sb.append(", preClosePrice=").append(preClosePrice);
        sb.append(", curPrice=").append(curPrice);
        sb.append(", maxPrice=").append(maxPrice);
        sb.append(", minPrice=").append(minPrice);
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", tradeVolume=").append(tradeVolume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}