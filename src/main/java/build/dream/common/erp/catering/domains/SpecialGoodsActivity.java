package build.dream.common.erp.catering.domains;

import build.dream.common.basic.BasicDomain;
import build.dream.common.constants.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class SpecialGoodsActivity extends BasicDomain {
    /**
     * 商户id
     */
    private BigInteger tenantId;
    /**
     * 商户编码
     */
    private String tenantCode;
    /**
     * 活动id
     */
    private BigInteger activityId;
    /**
     * 购买商品id
     */
    private BigInteger goodsId;
    /**
     * 购买商品规格id
     */
    private BigInteger goodsSpecificationId;
    /**
     * 优惠方式，1-特价，2-折扣
     */
    private Integer discountType;
    /**
     * 特价金额
     */
    private BigDecimal specialPrice = Constants.DECIMAL_DEFAULT_VALUE;
    /**
     * 折扣率
     */
    private BigDecimal discountRate = Constants.DECIMAL_DEFAULT_VALUE;

    public BigInteger getTenantId() {
        return tenantId;
    }

    public void setTenantId(BigInteger tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public BigInteger getActivityId() {
        return activityId;
    }

    public void setActivityId(BigInteger activityId) {
        this.activityId = activityId;
    }

    public BigInteger getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(BigInteger goodsId) {
        this.goodsId = goodsId;
    }

    public BigInteger getGoodsSpecificationId() {
        return goodsSpecificationId;
    }

    public void setGoodsSpecificationId(BigInteger goodsSpecificationId) {
        this.goodsSpecificationId = goodsSpecificationId;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public static class Builder {
        private final SpecialGoodsActivity instance = new SpecialGoodsActivity();

        public Builder tenantId(BigInteger tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder tenantCode(String tenantCode) {
            instance.setTenantCode(tenantCode);
            return this;
        }

        public Builder activityId(BigInteger activityId) {
            instance.setActivityId(activityId);
            return this;
        }

        public Builder goodsId(BigInteger goodsId) {
            instance.setGoodsId(goodsId);
            return this;
        }

        public Builder goodsSpecificationId(BigInteger goodsSpecificationId) {
            instance.setGoodsSpecificationId(goodsSpecificationId);
            return this;
        }

        public Builder discountType(Integer discountType) {
            instance.setDiscountType(discountType);
            return this;
        }

        public Builder specialPrice(BigDecimal specialPrice) {
            instance.setSpecialPrice(specialPrice);
            return this;
        }

        public Builder discountRate(BigDecimal discountRate) {
            instance.setDiscountRate(discountRate);
            return this;
        }

        public Builder id(BigInteger id) {
            instance.setId(id);
            return this;
        }

        public Builder createTime(Date createTime) {
            instance.setCreateTime(createTime);
            return this;
        }

        public Builder createUserId(BigInteger createUserId) {
            instance.setCreateUserId(createUserId);
            return this;
        }

        public Builder lastUpdateTime(Date lastUpdateTime) {
            instance.setLastUpdateTime(lastUpdateTime);
            return this;
        }

        public Builder lastUpdateUserId(BigInteger lastUpdateUserId) {
            instance.setLastUpdateUserId(lastUpdateUserId);
            return this;
        }

        public Builder lastUpdateRemark(String lastUpdateRemark) {
            instance.setLastUpdateRemark(lastUpdateRemark);
            return this;
        }

        public Builder deleteTime(Date deleteTime) {
            instance.setDeleteTime(deleteTime);
            return this;
        }

        public Builder deleted(boolean deleted) {
            instance.setDeleted(deleted);
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
