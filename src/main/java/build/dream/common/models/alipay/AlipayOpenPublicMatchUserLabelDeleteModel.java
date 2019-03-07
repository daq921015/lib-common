package build.dream.common.models.alipay;

public class AlipayOpenPublicMatchUserLabelDeleteModel extends AlipayBasicModel {
    public static class Builder {
        private final AlipayOpenPublicMatchUserLabelDeleteModel instance = new AlipayOpenPublicMatchUserLabelDeleteModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public AlipayOpenPublicMatchUserLabelDeleteModel build() {
            AlipayOpenPublicMatchUserLabelDeleteModel alipayOpenPublicMatchUserLabelDeleteModel = new AlipayOpenPublicMatchUserLabelDeleteModel();
            alipayOpenPublicMatchUserLabelDeleteModel.setTenantId(instance.getTenantId());
            alipayOpenPublicMatchUserLabelDeleteModel.setBranchId(instance.getBranchId());
            return alipayOpenPublicMatchUserLabelDeleteModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
