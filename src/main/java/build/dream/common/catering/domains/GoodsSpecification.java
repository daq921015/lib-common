package build.dream.common.catering.domains;

import build.dream.common.annotations.ShardingColumn;
import build.dream.common.basic.BasicDomain;
import build.dream.common.constants.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@ShardingColumn(fieldName = GoodsSpecification.FieldName.TENANT_ID, columnName = GoodsSpecification.ColumnName.TENANT_ID)
public class GoodsSpecification extends BasicDomain {
    public static final String TABLE_NAME = "goods_specification";

    /**
     * 商户ID
     */
    private BigInteger tenantId;
    /**
     * 商户编号
     */
    private String tenantCode;
    /**
     * 门店ID
     */
    private BigInteger branchId;
    /**
     * 商品ID
     */
    private BigInteger goodsId;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 口味加价
     */
    private BigDecimal price = Constants.DECIMAL_DEFAULT_VALUE;
    /**
     * 库存数量
     */
    private BigDecimal stock;

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

    public BigInteger getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(BigInteger goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public static class Builder {
        private final GoodsSpecification instance = new GoodsSpecification();

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

        public Builder goodsId(BigInteger goodsId) {
            instance.setGoodsId(goodsId);
            return this;
        }

        public Builder name(String name) {
            instance.setName(name);
            return this;
        }

        public Builder price(BigDecimal price) {
            instance.setPrice(price);
            return this;
        }

        public Builder stock(BigDecimal stock) {
            instance.setStock(stock);
            return this;
        }

        public Builder id(BigInteger id) {
            instance.setId(id);
            return this;
        }

        public Builder createdTime(Date createdTime) {
            instance.setCreatedTime(createdTime);
            return this;
        }

        public Builder createdUserId(BigInteger createdUserId) {
            instance.setCreatedUserId(createdUserId);
            return this;
        }

        public Builder updatedTime(Date updatedTime) {
            instance.setUpdatedTime(updatedTime);
            return this;
        }

        public Builder updatedUserId(BigInteger updatedUserId) {
            instance.setUpdatedUserId(updatedUserId);
            return this;
        }

        public Builder updatedRemark(String updatedRemark) {
            instance.setUpdatedRemark(updatedRemark);
            return this;
        }

        public Builder deletedTime(Date deletedTime) {
            instance.setDeletedTime(deletedTime);
            return this;
        }

        public Builder deleted(boolean deleted) {
            instance.setDeleted(deleted);
            return this;
        }

        public GoodsSpecification build() {
            GoodsSpecification goodsSpecification = new GoodsSpecification();
            goodsSpecification.setTenantId(instance.getTenantId());
            goodsSpecification.setTenantCode(instance.getTenantCode());
            goodsSpecification.setBranchId(instance.getBranchId());
            goodsSpecification.setGoodsId(instance.getGoodsId());
            goodsSpecification.setName(instance.getName());
            goodsSpecification.setPrice(instance.getPrice());
            goodsSpecification.setStock(instance.getStock());
            goodsSpecification.setId(instance.getId());
            goodsSpecification.setCreatedTime(instance.getCreatedTime());
            goodsSpecification.setCreatedUserId(instance.getCreatedUserId());
            goodsSpecification.setUpdatedTime(instance.getUpdatedTime());
            goodsSpecification.setUpdatedUserId(instance.getUpdatedUserId());
            goodsSpecification.setUpdatedRemark(instance.getUpdatedRemark());
            goodsSpecification.setDeletedTime(instance.getDeletedTime());
            goodsSpecification.setDeleted(instance.isDeleted());
            return goodsSpecification;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class ColumnName extends BasicDomain.ColumnName {
        public static final String TENANT_ID = "tenant_id";
        public static final String TENANT_CODE = "tenant_code";
        public static final String BRANCH_ID = "branch_id";
        public static final String GOODS_ID = "goods_id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String STOCK = "stock";
    }

    public static final class FieldName extends BasicDomain.FieldName {
        public static final String TENANT_ID = "tenantId";
        public static final String TENANT_CODE = "tenantCode";
        public static final String BRANCH_ID = "branchId";
        public static final String GOODS_ID = "goodsId";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String STOCK = "stock";
    }
}
