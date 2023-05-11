package com.scy.stock.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 股票大盘数据详情表
 * @TableName stock_market_index_info
 */
@Data
public class StockMarketIndexInfo implements Serializable {
    /**
     * 主键字段（无业务意义）
     */
    private String id;

    /**
     * 大盘ID
     */
    private String markId;

    /**
     * 当前时间
     */
    private Date curTime;

    /**
     * 指数名称
     */
    private String markName;

    /**
     * 当前点数
     */
    private BigDecimal curPoint;

    /**
     * 当前价格
     */
    private BigDecimal currentPrice;

    /**
     * 涨跌率
     */
    private BigDecimal updownRate;

    /**
     * 成交量（多少手）
     */
    private Long tradeAccount;

    /**
     * 成交额(万元)
     */
    private Long tradeVolume;

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
        StockMarketIndexInfo other = (StockMarketIndexInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMarkId() == null ? other.getMarkId() == null : this.getMarkId().equals(other.getMarkId()))
            && (this.getCurTime() == null ? other.getCurTime() == null : this.getCurTime().equals(other.getCurTime()))
            && (this.getMarkName() == null ? other.getMarkName() == null : this.getMarkName().equals(other.getMarkName()))
            && (this.getCurPoint() == null ? other.getCurPoint() == null : this.getCurPoint().equals(other.getCurPoint()))
            && (this.getCurrentPrice() == null ? other.getCurrentPrice() == null : this.getCurrentPrice().equals(other.getCurrentPrice()))
            && (this.getUpdownRate() == null ? other.getUpdownRate() == null : this.getUpdownRate().equals(other.getUpdownRate()))
            && (this.getTradeAccount() == null ? other.getTradeAccount() == null : this.getTradeAccount().equals(other.getTradeAccount()))
            && (this.getTradeVolume() == null ? other.getTradeVolume() == null : this.getTradeVolume().equals(other.getTradeVolume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMarkId() == null) ? 0 : getMarkId().hashCode());
        result = prime * result + ((getCurTime() == null) ? 0 : getCurTime().hashCode());
        result = prime * result + ((getMarkName() == null) ? 0 : getMarkName().hashCode());
        result = prime * result + ((getCurPoint() == null) ? 0 : getCurPoint().hashCode());
        result = prime * result + ((getCurrentPrice() == null) ? 0 : getCurrentPrice().hashCode());
        result = prime * result + ((getUpdownRate() == null) ? 0 : getUpdownRate().hashCode());
        result = prime * result + ((getTradeAccount() == null) ? 0 : getTradeAccount().hashCode());
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
        sb.append(", markId=").append(markId);
        sb.append(", curTime=").append(curTime);
        sb.append(", markName=").append(markName);
        sb.append(", curPoint=").append(curPoint);
        sb.append(", currentPrice=").append(currentPrice);
        sb.append(", updownRate=").append(updownRate);
        sb.append(", tradeAccount=").append(tradeAccount);
        sb.append(", tradeVolume=").append(tradeVolume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}