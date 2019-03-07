package build.dream.common.models.alipay;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class AlipayOpenAgentSignStatusQueryModel extends AlipayBasicModel {
    @NotEmpty
    private List<String> productCodes;

    public List<String> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<String> productCodes) {
        this.productCodes = productCodes;
    }

    public static class Builder {
        private final AlipayOpenAgentSignStatusQueryModel instance = new AlipayOpenAgentSignStatusQueryModel();

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

        public Builder productCodes(List<String> productCodes) {
            instance.setProductCodes(productCodes);
            return this;
        }

        public AlipayOpenAgentSignStatusQueryModel build() {
            AlipayOpenAgentSignStatusQueryModel alipayOpenAgentSignStatusQueryModel = new AlipayOpenAgentSignStatusQueryModel();
            alipayOpenAgentSignStatusQueryModel.setTenantId(instance.getTenantId());
            alipayOpenAgentSignStatusQueryModel.setBranchId(instance.getBranchId());
            alipayOpenAgentSignStatusQueryModel.setReturnUrl(instance.getReturnUrl());
            alipayOpenAgentSignStatusQueryModel.setNotifyUrl(instance.getNotifyUrl());
            alipayOpenAgentSignStatusQueryModel.setAuthToken(instance.getAuthToken());
            alipayOpenAgentSignStatusQueryModel.setProductCodes(instance.getProductCodes());
            return alipayOpenAgentSignStatusQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
