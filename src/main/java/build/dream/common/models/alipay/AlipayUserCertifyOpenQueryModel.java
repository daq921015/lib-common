package build.dream.common.models.alipay;

import build.dream.common.models.BasicModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayUserCertifyOpenQueryModel extends BasicModel {
    @NotNull
    @JsonIgnore
    private String tenantId;

    @NotNull
    @JsonIgnore
    private String branchId;

    @NotNull
    @Length(max = 32)
    @JsonProperty(value = "certify_id")
    private String certifyId;

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

        public Builder certifyId(String certifyId) {
            instance.setCertifyId(certifyId);
            return this;
        }

        public AlipayUserCertifyOpenQueryModel build() {
            AlipayUserCertifyOpenQueryModel alipayUserCertifyOpenQueryModel = new AlipayUserCertifyOpenQueryModel();
            alipayUserCertifyOpenQueryModel.setTenantId(instance.getTenantId());
            alipayUserCertifyOpenQueryModel.setBranchId(instance.getBranchId());
            return alipayUserCertifyOpenQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
