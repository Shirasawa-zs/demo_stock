package com.scy.stock.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 主营业务表
 * @TableName stock_business
 */
@Data
public class StockBusiness implements Serializable {
    /**
     *  股票编码
     */
    private String secCode;

    /**
     * 股票名称
     */
    private String secName;

    /**
     * 行业板块代码
     */
    private String sectorCode;

    /**
     * 行业板块名称
     */
    private String sectorName;

    /**
     * 主营业务
     */
    private String business;

    /**
     * 更新时间
     */
    private Date updateTime;

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
        StockBusiness other = (StockBusiness) that;
        return (this.getSecCode() == null ? other.getSecCode() == null : this.getSecCode().equals(other.getSecCode()))
            && (this.getSecName() == null ? other.getSecName() == null : this.getSecName().equals(other.getSecName()))
            && (this.getSectorCode() == null ? other.getSectorCode() == null : this.getSectorCode().equals(other.getSectorCode()))
            && (this.getSectorName() == null ? other.getSectorName() == null : this.getSectorName().equals(other.getSectorName()))
            && (this.getBusiness() == null ? other.getBusiness() == null : this.getBusiness().equals(other.getBusiness()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSecCode() == null) ? 0 : getSecCode().hashCode());
        result = prime * result + ((getSecName() == null) ? 0 : getSecName().hashCode());
        result = prime * result + ((getSectorCode() == null) ? 0 : getSectorCode().hashCode());
        result = prime * result + ((getSectorName() == null) ? 0 : getSectorName().hashCode());
        result = prime * result + ((getBusiness() == null) ? 0 : getBusiness().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", secCode=").append(secCode);
        sb.append(", secName=").append(secName);
        sb.append(", sectorCode=").append(sectorCode);
        sb.append(", sectorName=").append(sectorName);
        sb.append(", business=").append(business);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}