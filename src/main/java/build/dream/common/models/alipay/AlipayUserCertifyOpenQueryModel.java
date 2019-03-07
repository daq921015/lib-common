package build.dream.common.models.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayUserCertifyOpenQueryModel extends AlipayBasicModel {
    @NotNull
    @Length(max = 32)
    @JsonProperty(value = "certify_id")
    private String certifyId;

    public String getCertifyId() {
        return certifyId;
    }

    public void setCertifyId(String certifyId) {
        this.certifyId = certifyId;
    }

    public static class Builder {
        private final AlipayUserCertifyOpenQueryModel instance = new AlipayUserCertifyOpenQueryModel();

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

        public Builder certifyId(String certifyId) {
            instance.setCertifyId(certifyId);
            return this;
        }

        public AlipayUserCertifyOpenQueryModel build() {
            AlipayUserCertifyOpenQueryModel alipayUserCertifyOpenQueryModel = new AlipayUserCertifyOpenQueryModel();
            alipayUserCertifyOpenQueryModel.setTenantId(instance.getTenantId());
            alipayUserCertifyOpenQueryModel.setBranchId(instance.getBranchId());
            alipayUserCertifyOpenQueryModel.setReturnUrl(instance.getReturnUrl());
            alipayUserCertifyOpenQueryModel.setNotifyUrl(instance.getNotifyUrl());
            alipayUserCertifyOpenQueryModel.setAuthToken(instance.getAuthToken());
            return alipayUserCertifyOpenQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
