package build.dream.common.models.alipay;

import build.dream.common.models.BasicModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayOpenPublicSingleArticleDataBatchQueryModel extends BasicModel {
    @NotNull
    @JsonIgnore
    private String tenantId;

    @NotNull
    @JsonIgnore
    private String branchId;

    @NotNull
    @Length(min = 8, max = 8)
    @JsonProperty(value = "begin_date")
    private String beginDate;

    @NotNull
    @Length(min = 8, max = 8)
    @JsonProperty(value = "end_date")
    private String endDate;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static class Builder {
        private final AlipayOpenPublicSingleArticleDataBatchQueryModel instance = new AlipayOpenPublicSingleArticleDataBatchQueryModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder beginDate(String beginDate) {
            instance.setBeginDate(beginDate);
            return this;
        }

        public Builder endDate(String endDate) {
            instance.setEndDate(endDate);
            return this;
        }

        public AlipayOpenPublicSingleArticleDataBatchQueryModel build() {
            AlipayOpenPublicSingleArticleDataBatchQueryModel alipayOpenPublicSingleArticleDataBatchQueryModel = new AlipayOpenPublicSingleArticleDataBatchQueryModel();
            alipayOpenPublicSingleArticleDataBatchQueryModel.setTenantId(instance.getTenantId());
            alipayOpenPublicSingleArticleDataBatchQueryModel.setBranchId(instance.getBranchId());
            alipayOpenPublicSingleArticleDataBatchQueryModel.setBeginDate(instance.getBeginDate());
            alipayOpenPublicSingleArticleDataBatchQueryModel.setEndDate(instance.getEndDate());
            return alipayOpenPublicSingleArticleDataBatchQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}