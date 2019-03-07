package build.dream.common.models.alipay;

import build.dream.common.constraints.InList;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AlipayPassInstanceAddModel extends AlipayBasicModel {
    @NotNull
    @Length(max = 999)
    @JsonProperty(value = "tpl_id")
    private String tplId;

    @NotNull
    @Length(max = 99999)
    @JsonProperty(value = "tpl_params")
    private String tplParams;

    @NotNull
    @InList(value = {"1"})
    @JsonProperty(value = "recognition_type")
    private String recognitionType;

    @NotNull
    @Length(max = 999)
    @JsonProperty(value = "recognition_info")
    private String recognitionInfo;

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getTplParams() {
        return tplParams;
    }

    public void setTplParams(String tplParams) {
        this.tplParams = tplParams;
    }

    public String getRecognitionType() {
        return recognitionType;
    }

    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public String getRecognitionInfo() {
        return recognitionInfo;
    }

    public void setRecognitionInfo(String recognitionInfo) {
        this.recognitionInfo = recognitionInfo;
    }

    public static class Builder {
        private final AlipayPassInstanceAddModel instance = new AlipayPassInstanceAddModel();

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

        public Builder tplId(String tplId) {
            instance.setTplId(tplId);
            return this;
        }

        public Builder tplParams(String tplParams) {
            instance.setTplParams(tplParams);
            return this;
        }

        public Builder recognitionType(String recognitionType) {
            instance.setRecognitionType(recognitionType);
            return this;
        }

        public Builder recognitionInfo(String recognitionInfo) {
            instance.setRecognitionInfo(recognitionInfo);
            return this;
        }

        public AlipayPassInstanceAddModel build() {
            AlipayPassInstanceAddModel alipayPassInstanceAddModel = new AlipayPassInstanceAddModel();
            alipayPassInstanceAddModel.setTenantId(instance.getTenantId());
            alipayPassInstanceAddModel.setBranchId(instance.getBranchId());
            alipayPassInstanceAddModel.setTplId(instance.getTplId());
            alipayPassInstanceAddModel.setTplParams(instance.getTplParams());
            alipayPassInstanceAddModel.setRecognitionType(instance.getRecognitionType());
            alipayPassInstanceAddModel.setRecognitionInfo(instance.getRecognitionInfo());
            return alipayPassInstanceAddModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
