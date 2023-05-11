package com.scy.stock.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 股票板块详情信息表
 * @TableName stock_block_rt_info
 */
@Data
public class StockBlockRtInfo implements Serializable {
    /**
     * 板块主键ID（业务无关）
     */
    private String id;

    /**
     * 表示，如：new_blhy-玻璃行业
     */
    private String label;

    /**
     * 板块名称
     */
    private String blockName;

    /**
     * 公司数量
     */
    private Integer companyNum;

    /**
     * 平均价格
     */
    private BigDecimal avgPrice;

    /**
     * 涨跌幅
     */
    private BigDecimal updownRate;

    /**
     * 交易量
     */
    private Long tradeAmount;

    /**
     * 交易金额
     */
    private BigDecimal tradeVolume;

    /**
     * 当前日期（精确到秒）
     */
    private Date curTime;

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
        StockBlockRtInfo other = (StockBlockRtInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
            && (this.getBlockName() == null ? other.getBlockName() == null : this.getBlockName().equals(other.getBlockName()))
            && (this.getCompanyNum() == null ? other.getCompanyNum() == null : this.getCompanyNum().equals(other.getCompanyNum()))
            && (this.getAvgPrice() == null ? other.getAvgPrice() == null : this.getAvgPrice().equals(other.getAvgPrice()))
            && (this.getUpdownRate() == null ? other.getUpdownRate() == null : this.getUpdownRate().equals(other.getUpdownRate()))
            && (this.getTradeAmount() == null ? other.getTradeAmount() == null : this.getTradeAmount().equals(other.getTradeAmount()))
            && (this.getTradeVolume() == null ? other.getTradeVolume() == null : this.getTradeVolume().equals(other.getTradeVolume()))
            && (this.getCurTime() == null ? other.getCurTime() == null : this.getCurTime().equals(other.getCurTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        result = prime * result + ((getBlockName() == null) ? 0 : getBlockName().hashCode());
        result = prime * result + ((getCompanyNum() == null) ? 0 : getCompanyNum().hashCode());
        result = prime * result + ((getAvgPrice() == null) ? 0 : getAvgPrice().hashCode());
        result = prime * result + ((getUpdownRate() == null) ? 0 : getUpdownRate().hashCode());
        result = prime * result + ((getTradeAmount() == null) ? 0 : getTradeAmount().hashCode());
        result = prime * result + ((getTradeVolume() == null) ? 0 : getTradeVolume().hashCode());
        result = prime * result + ((getCurTime() == null) ? 0 : getCurTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", label=").append(label);
        sb.append(", blockName=").append(blockName);
        sb.append(", companyNum=").append(companyNum);
        sb.append(", avgPrice=").append(avgPrice);
        sb.append(", updownRate=").append(updownRate);
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", tradeVolume=").append(tradeVolume);
        sb.append(", curTime=").append(curTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}