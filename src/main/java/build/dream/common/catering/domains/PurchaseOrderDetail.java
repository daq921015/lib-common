package build.dream.common.catering.domains;

import build.dream.common.basic.BasicDomain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class PurchaseOrderDetail extends BasicDomain {
    private BigInteger tenantId;
    private String tenantCode;
    private BigInteger branchId;
    private BigInteger purchaseOrderId;
    private BigInteger goodsId;
    private BigInteger goodsSpecificationId;
    private BigInteger unitId;
    private BigDecimal purchasePrice;
    private BigDecimal quantity;

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

    public BigInteger getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(BigInteger purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public BigInteger getUnitId() {
        return unitId;
    }

    public void setUnitId(BigInteger unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public static class Builder {
        private final PurchaseOrderDetail instance = new PurchaseOrderDetail();

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

        public Builder purchaseOrderId(BigInteger purchaseOrderId) {
            instance.setPurchaseOrderId(purchaseOrderId);
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

        public Builder unitId(BigInteger unitId) {
            instance.setUnitId(unitId);
            return this;
        }

        public Builder purchasePrice(BigDecimal purchasePrice) {
            instance.setPurchasePrice(purchasePrice);
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            instance.setQuantity(quantity);
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

        public PurchaseOrderDetail build() {
            return instance;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class ColumnName extends BasicDomain.ColumnName {
        public static final String TENANT_ID = "tenant_id";
        public static final String TENANT_CODE = "tenant_code";
        public static final String BRANCH_ID = "branch_id";
        public static final String PURCHASE_ORDER_ID = "purchase_order_id";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_SPECIFICATION_ID = "goods_specification_id";
        public static final String UNIT_ID = "unit_id";
        public static final String PURCHASE_PRICE = "purchase_price";
        public static final String QUANTITY = "quantity";
    }

    public static final class FieldName extends BasicDomain.FieldName {
        public static final String TENANT_ID = "tenantId";
        public static final String TENANT_CODE = "tenantCode";
        public static final String BRANCH_ID = "branchId";
        public static final String PURCHASE_ORDER_ID = "purchaseOrderId";
        public static final String GOODS_ID = "goodsId";
        public static final String GOODS_SPECIFICATION_ID = "goodsSpecificationId";
        public static final String UNIT_ID = "unitId";
        public static final String PURCHASE_PRICE = "purchasePrice";
        public static final String QUANTITY = "quantity";
    }
}
