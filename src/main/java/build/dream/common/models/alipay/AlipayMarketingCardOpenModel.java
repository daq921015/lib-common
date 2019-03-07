package build.dream.common.models.alipay;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

public class AlipayMarketingCardOpenModel extends AlipayBasicModel {
    @NotNull
    @JsonIgnore
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public static class Builder {
        private final AlipayMarketingCardOpenModel instance = new AlipayMarketingCardOpenModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder authToken(String authToken) {
            instance.setAuthToken(authToken);
            return this;
        }

        public AlipayMarketingCardOpenModel build() {
            AlipayMarketingCardOpenModel alipayMarketingCardOpenModel = new AlipayMarketingCardOpenModel();
            alipayMarketingCardOpenModel.setTenantId(instance.getTenantId());
            alipayMarketingCardOpenModel.setBranchId(instance.getBranchId());
            alipayMarketingCardOpenModel.setAuthToken(instance.getAuthToken());
            return alipayMarketingCardOpenModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
