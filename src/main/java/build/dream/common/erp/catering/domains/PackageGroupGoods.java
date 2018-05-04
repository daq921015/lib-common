package build.dream.common.erp.catering.domains;

import build.dream.common.basic.BasicDomain;

import java.math.BigInteger;

public class PackageGroupGoods extends BasicDomain {
    private BigInteger packageGroupId;
    private BigInteger goodsId;
    private BigInteger goodsSpecificationId;
    private Integer quantity;

    public BigInteger getPackageGroupId() {
        return packageGroupId;
    }

    public void setPackageGroupId(BigInteger packageGroupId) {
        this.packageGroupId = packageGroupId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
