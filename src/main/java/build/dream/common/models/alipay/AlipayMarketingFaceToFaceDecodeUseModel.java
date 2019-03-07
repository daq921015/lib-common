package build.dream.common.models.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayMarketingFaceToFaceDecodeUseModel extends AlipayBasicModel {
    @NotNull
    @Length(max = 32)
    @JsonProperty(value = "dynamic_id")
    private String dynamicId;

    @NotNull
    @Length(max = 128)
    @JsonProperty(value = "sence_no")
    private String senceNo;

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getSenceNo() {
        return senceNo;
    }

    public void setSenceNo(String senceNo) {
        this.senceNo = senceNo;
    }

    public static class Builder {
        private final AlipayMarketingFaceToFaceDecodeUseModel instance = new AlipayMarketingFaceToFaceDecodeUseModel();

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

        public Builder dynamicId(String dynamicId) {
            instance.setDynamicId(dynamicId);
            return this;
        }

        public Builder senceNo(String senceNo) {
            instance.setSenceNo(senceNo);
            return this;
        }

        public AlipayMarketingFaceToFaceDecodeUseModel build() {
            AlipayMarketingFaceToFaceDecodeUseModel alipayMarketingFaceToFaceDecodeUseModel = new AlipayMarketingFaceToFaceDecodeUseModel();
            alipayMarketingFaceToFaceDecodeUseModel.setTenantId(instance.getTenantId());
            alipayMarketingFaceToFaceDecodeUseModel.setBranchId(instance.getBranchId());
            alipayMarketingFaceToFaceDecodeUseModel.setReturnUrl(instance.getReturnUrl());
            alipayMarketingFaceToFaceDecodeUseModel.setNotifyUrl(instance.getNotifyUrl());
            alipayMarketingFaceToFaceDecodeUseModel.setAuthToken(instance.getAuthToken());
            alipayMarketingFaceToFaceDecodeUseModel.setDynamicId(instance.getDynamicId());
            alipayMarketingFaceToFaceDecodeUseModel.setSenceNo(instance.getSenceNo());
            return alipayMarketingFaceToFaceDecodeUseModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
