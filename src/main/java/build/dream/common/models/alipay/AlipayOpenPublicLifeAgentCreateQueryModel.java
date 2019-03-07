package build.dream.common.models.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayOpenPublicLifeAgentCreateQueryModel extends AlipayBasicModel {
    @NotNull
    @Length(max = 64)
    @JsonProperty(value = "out_biz_no")
    private String outBizNo;

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public static class Builder {
        private final AlipayOpenPublicLifeAgentCreateQueryModel instance = new AlipayOpenPublicLifeAgentCreateQueryModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder outBizNo(String outBizNo) {
            instance.setOutBizNo(outBizNo);
            return this;
        }

        public AlipayOpenPublicLifeAgentCreateQueryModel build() {
            AlipayOpenPublicLifeAgentCreateQueryModel alipayOpenPublicLifeAgentCreateQueryModel = new AlipayOpenPublicLifeAgentCreateQueryModel();
            alipayOpenPublicLifeAgentCreateQueryModel.setTenantId(instance.getTenantId());
            alipayOpenPublicLifeAgentCreateQueryModel.setBranchId(instance.getBranchId());
            alipayOpenPublicLifeAgentCreateQueryModel.setOutBizNo(instance.getOutBizNo());
            return alipayOpenPublicLifeAgentCreateQueryModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
