package build.dream.common.erp.catering.domains;

import build.dream.common.basic.BasicDomain;
import build.dream.common.constants.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class DietOrderDetailGoodsAttribute extends BasicDomain {
    /**
     * 商户ID
     */
    private BigInteger tenantId;
    /**
     * 商户编码
     */
    private String tenantCode;
    /**
     * 门店ID
     */
    private BigInteger branchId;
    /**
     * diet_order.id
     */
    private BigInteger dietOrderId;
    /**
     * 订单组id
     */
    private BigInteger dietOrderGroupId;
    /**
     * 餐厅订单明细ID，diet_order_detail.id
     */
    private BigInteger dietOrderDetailId;
    /**
     * 口味组ID，goods_attribute_group.id
     */
    private BigInteger goodsAttributeGroupId;
    /**
     * 口味组名称，goods_attribute_group.name
     */
    private String goodsAttributeGroupName;
    /**
     * 口味ID，goods_attribute.id
     */
    private BigInteger goodsAttributeId;
    /**
     * 口味名称，goods_attribute.name
     */
    private String goodsAttributeName;
    /**
     * 口味加价，goods_attribute.price
     */
    private BigDecimal price = Constants.DECIMAL_DEFAULT_VALUE;
    /**
     * 本地ID
     */
    private String localId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 本地订单ID
     */
    private String localDietOrderId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 本地订单组ID
     */
    private String localDietOrderGroupId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 本地订单详情ID
     */
    private String localDietOrderDetailId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 本地创建时间
     */
    private Date localCreateTime = Constants.DATETIME_DEFAULT_VALUE;
    /**
     * 本地最后更新时间
     */
    private Date localLastUpdateTime = Constants.DATETIME_DEFAULT_VALUE;

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

    public BigInteger getBranchId() {
        return branchId;
    }

    public void setBranchId(BigInteger branchId) {
        this.branchId = branchId;
    }

    public BigInteger getDietOrderId() {
        return dietOrderId;
    }

    public void setDietOrderId(BigInteger dietOrderId) {
        this.dietOrderId = dietOrderId;
    }

    public BigInteger getDietOrderGroupId() {
        return dietOrderGroupId;
    }

    public void setDietOrderGroupId(BigInteger dietOrderGroupId) {
        this.dietOrderGroupId = dietOrderGroupId;
    }

    public BigInteger getDietOrderDetailId() {
        return dietOrderDetailId;
    }

    public void setDietOrderDetailId(BigInteger dietOrderDetailId) {
        this.dietOrderDetailId = dietOrderDetailId;
    }

    public BigInteger getGoodsAttributeGroupId() {
        return goodsAttributeGroupId;
    }

    public void setGoodsAttributeGroupId(BigInteger goodsAttributeGroupId) {
        this.goodsAttributeGroupId = goodsAttributeGroupId;
    }

    public String getGoodsAttributeGroupName() {
        return goodsAttributeGroupName;
    }

    public void setGoodsAttributeGroupName(String goodsAttributeGroupName) {
        this.goodsAttributeGroupName = goodsAttributeGroupName;
    }

    public BigInteger getGoodsAttributeId() {
        return goodsAttributeId;
    }

    public void setGoodsAttributeId(BigInteger goodsAttributeId) {
        this.goodsAttributeId = goodsAttributeId;
    }

    public String getGoodsAttributeName() {
        return goodsAttributeName;
    }

    public void setGoodsAttributeName(String goodsAttributeName) {
        this.goodsAttributeName = goodsAttributeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getLocalDietOrderId() {
        return localDietOrderId;
    }

    public void setLocalDietOrderId(String localDietOrderId) {
        this.localDietOrderId = localDietOrderId;
    }

    public String getLocalDietOrderGroupId() {
        return localDietOrderGroupId;
    }

    public void setLocalDietOrderGroupId(String localDietOrderGroupId) {
        this.localDietOrderGroupId = localDietOrderGroupId;
    }

    public String getLocalDietOrderDetailId() {
        return localDietOrderDetailId;
    }

    public void setLocalDietOrderDetailId(String localDietOrderDetailId) {
        this.localDietOrderDetailId = localDietOrderDetailId;
    }

    public Date getLocalCreateTime() {
        return localCreateTime;
    }

    public void setLocalCreateTime(Date localCreateTime) {
        this.localCreateTime = localCreateTime;
    }

    public Date getLocalLastUpdateTime() {
        return localLastUpdateTime;
    }

    public void setLocalLastUpdateTime(Date localLastUpdateTime) {
        this.localLastUpdateTime = localLastUpdateTime;
    }

    public static class Builder {
        private final DietOrderDetailGoodsAttribute instance = new DietOrderDetailGoodsAttribute();

        public Builder tenantId(BigInteger tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder tenantCode(String tenantCode) {
            instance.setTenantCode(tenantCode);
            return this;
        }

        public Builder branchId(BigInteger branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder dietOrderId(BigInteger dietOrderId) {
            instance.setDietOrderId(dietOrderId);
            return this;
        }

        public Builder dietOrderGroupId(BigInteger dietOrderGroupId) {
            instance.setDietOrderGroupId(dietOrderGroupId);
            return this;
        }

        public Builder dietOrderDetailId(BigInteger dietOrderDetailId) {
            instance.setDietOrderDetailId(dietOrderDetailId);
            return this;
        }

        public Builder goodsAttributeGroupId(BigInteger goodsAttributeGroupId) {
            instance.setGoodsAttributeGroupId(goodsAttributeGroupId);
            return this;
        }

        public Builder goodsAttributeGroupName(String goodsAttributeGroupName) {
            instance.setGoodsAttributeGroupName(goodsAttributeGroupName);
            return this;
        }

        public Builder goodsAttributeId(BigInteger goodsAttributeId) {
            instance.setGoodsAttributeId(goodsAttributeId);
            return this;
        }

        public Builder goodsAttributeName(String goodsAttributeName) {
            instance.setGoodsAttributeName(goodsAttributeName);
            return this;
        }

        public Builder price(BigDecimal price) {
            instance.setPrice(price);
            return this;
        }

        public Builder localId(String localId) {
            instance.setLocalId(localId);
            return this;
        }

        public Builder localDietOrderId(String localDietOrderId) {
            instance.setLocalDietOrderId(localDietOrderId);
            return this;
        }

        public Builder localDietOrderGroupId(String localDietOrderGroupId) {
            instance.setLocalDietOrderGroupId(localDietOrderGroupId);
            return this;
        }

        public Builder localDietOrderDetailId(String localDietOrderDetailId) {
            instance.setLocalDietOrderDetailId(localDietOrderDetailId);
            return this;
        }

        public Builder localCreateTime(Date localCreateTime) {
            instance.setLocalCreateTime(localCreateTime);
            return this;
        }

        public Builder localLastUpdateTime(Date localLastUpdateTime) {
            instance.setLocalLastUpdateTime(localLastUpdateTime);
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

        public Builder deleted(boolean deleted) {
            instance.setDeleted(deleted);
            return this;
        }

        public DietOrderDetailGoodsAttribute build() {
            return instance;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
