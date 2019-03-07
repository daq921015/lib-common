package build.dream.common.models.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AlipayMarketingVoucherTemplateListQueryModel extends AlipayBasicModel {
    @NotNull
    @Length(min = 19, max = 19)
    @JsonProperty(value = "create_start_time")
    private String createStartTime;

    @NotNull
    @Length(min = 19, max = 19)
    @JsonProperty(value = "create_end_time")
    private String createEndTime;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999999999L)
    private Long pageNum;

    @NotNull
    @Min(value = 1)
    @Max(value = 30)
    @JsonProperty(value = "page_size")
    private Integer pageSize;

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public static class Builder {
        private final AlipayMarketingVoucherTemplateListQueryModel instance = new AlipayMarketingVoucherTemplateListQueryModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder returnUrl(String returnUrl) {
            instance.setReturnUrl(returnUrl);
            return this;
        }

        public Builder notifyUrl(String notifyUrl) {
            instance.setNotifyUrl(notifyUrl);
            return this;
        }

        public Builder authToken(String authToken) {
            instance.setAuthToken(authToken);
            return this;
        }

        public Builder createStartTime(String createStartTime) {
            instance.setCreateStartTime(createStartTime);
            return this;
        }

        public Builder createEndTime(String createEndTime) {
            instance.setCreateEndTime(createEndTime);
            return this;
        }

        public Builder pageNum(Long pageNum) {
            instance.setPageNum(pageNum);
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            instance.setPageSize(pageSize);
            return this;
        }

        public AlipayMarketingVoucherTemplateListQueryModel build() {
            AlipayMarketingVoucherTemplateListQueryModel alipayMarketingVoucherTemplateListQueryModel = new AlipayMarketingVoucherTemplateListQueryModel();
            alipayMarketingVoucherTemplateListQueryModel.setTenantId(instance.getTenantId());
            alipayMarketingVoucherTemplateListQueryModel.setBranchId(instance.getBranchId());
            alipayMarketingVoucherTemplateListQueryModel.setReturnUrl(instance.getReturnUrl());
            alipayMarketingVoucherTemplateListQueryModel.setNotifyUrl(instance.getNotifyUrl());
            alipayMarketingVoucherTemplateListQueryModel.setAuthToken(instance.getAuthToken());
            alipayMarketingVoucherTemplateListQueryModel.setCreateStartTime(instance.getCreateStartTime());
            alipayMarketingVoucherTemplateListQueryModel.setCreateEndTime(instance.getCreateEndTime());
            alipayMarketingVoucherTemplateListQueryModel.setPageNum(instance.getPageNum());
            alipayMarketingVoucherTemplateListQueryModel.setPageSize(instance.getPageSize());
            return alipayMarketingVoucherTemplateListQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
