package build.dream.common.models.alipay;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayMarketingUseRulePidQueryModel extends AlipayBasicModel {
    @NotNull
    @Length(max = 16)
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public static class Builder {
        private final AlipayMarketingUseRulePidQueryModel instance = new AlipayMarketingUseRulePidQueryModel();

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

        public Builder pid(String pid) {
            instance.setPid(pid);
            return this;
        }

        public AlipayMarketingUseRulePidQueryModel build() {
            AlipayMarketingUseRulePidQueryModel alipayMarketingUseRulePidQueryModel = new AlipayMarketingUseRulePidQueryModel();
            alipayMarketingUseRulePidQueryModel.setTenantId(instance.getTenantId());
            alipayMarketingUseRulePidQueryModel.setBranchId(instance.getBranchId());
            alipayMarketingUseRulePidQueryModel.setReturnUrl(instance.getReturnUrl());
            alipayMarketingUseRulePidQueryModel.setNotifyUrl(instance.getNotifyUrl());
            alipayMarketingUseRulePidQueryModel.setAuthToken(instance.getAuthToken());
            alipayMarketingUseRulePidQueryModel.setPid(instance.getPid());
            return alipayMarketingUseRulePidQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
