package com.scy.stock.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 股票大盘 开盘价与前收盘价流水表
 * @TableName stock_market_log_price
 */
@Data
public class StockMarketLogPrice implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 大盘编码
     */
    private String marketCode;

    /**
     * 当前日期
     */
    private Date curDate;

    /**
     * 前收盘价格
     */
    private BigDecimal preClosePrice;

    /**
     * 开盘价格
     */
    private BigDecimal openPrice;

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
        StockMarketLogPrice other = (StockMarketLogPrice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMarketCode() == null ? other.getMarketCode() == null : this.getMarketCode().equals(other.getMarketCode()))
            && (this.getCurDate() == null ? other.getCurDate() == null : this.getCurDate().equals(other.getCurDate()))
            && (this.getPreClosePrice() == null ? other.getPreClosePrice() == null : this.getPreClosePrice().equals(other.getPreClosePrice()))
            && (this.getOpenPrice() == null ? other.getOpenPrice() == null : this.getOpenPrice().equals(other.getOpenPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMarketCode() == null) ? 0 : getMarketCode().hashCode());
        result = prime * result + ((getCurDate() == null) ? 0 : getCurDate().hashCode());
        result = prime * result + ((getPreClosePrice() == null) ? 0 : getPreClosePrice().hashCode());
        result = prime * result + ((getOpenPrice() == null) ? 0 : getOpenPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", marketCode=").append(marketCode);
        sb.append(", curDate=").append(curDate);
        sb.append(", preClosePrice=").append(preClosePrice);
        sb.append(", openPrice=").append(openPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}