package build.dream.common.models.alipay;

import build.dream.common.constraints.InList;
import build.dream.common.models.BasicModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class KoubeiTradeTicketTicketCodeCancelModel extends BasicModel {
    @NotNull
    @JsonIgnore
    private String tenantId;

    @NotNull
    @JsonIgnore
    private String branchId;

    @NotNull
    @Length(max = 32)
    @JsonProperty(value = "request_id")
    private String requestId;

    @NotNull
    @Length(max = 64)
    @JsonProperty(value = "request_biz_no")
    private String requestBizNo;

    @NotNull
    @Length(max = 32)
    @JsonProperty(value = "ticket_code")
    private String ticketCode;

    @Length(max = 4)
    private String quantity;

    @Length(max = 32)
    @JsonProperty(value = "order_no")
    private String orderNo;

    @InList(value = {"INTERNAL_CODE", "EXTERNAL_CODE"})
    @JsonProperty(value = "code_type")
    private String codeType;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestBizNo() {
        return requestBizNo;
    }

    public void setRequestBizNo(String requestBizNo) {
        this.requestBizNo = requestBizNo;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public static class Builder {
        private final KoubeiTradeTicketTicketCodeCancelModel instance = new KoubeiTradeTicketTicketCodeCancelModel();

        public Builder tenantId(String tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder branchId(String branchId) {
            instance.setBranchId(branchId);
            return this;
        }

        public Builder requestId(String requestId) {
            instance.setRequestBizNo(requestId);
            return this;
        }

        public Builder requestBizNo(String requestBizNo) {
            instance.setRequestBizNo(requestBizNo);
            return this;
        }

        public Builder ticketCode(String ticketCode) {
            instance.setTicketCode(ticketCode);
            return this;
        }

        public Builder quantity(String quantity) {
            instance.setQuantity(quantity);
            return this;
        }

        public Builder orderNo(String orderNo) {
            instance.setOrderNo(orderNo);
            return this;
        }

        public Builder codeType(String codeType) {
            instance.setCodeType(codeType);
            return this;
        }

        public KoubeiTradeTicketTicketCodeCancelModel build() {
            KoubeiTradeTicketTicketCodeCancelModel koubeiTradeTicketTicketCodeCancelModel = new KoubeiTradeTicketTicketCodeCancelModel();
            koubeiTradeTicketTicketCodeCancelModel.setTenantId(instance.getTenantId());
            koubeiTradeTicketTicketCodeCancelModel.setBranchId(instance.getBranchId());
            koubeiTradeTicketTicketCodeCancelModel.setRequestId(instance.getRequestId());
            koubeiTradeTicketTicketCodeCancelModel.setRequestBizNo(instance.getRequestBizNo());
            koubeiTradeTicketTicketCodeCancelModel.setTicketCode(instance.getTicketCode());
            koubeiTradeTicketTicketCodeCancelModel.setQuantity(instance.getQuantity());
            koubeiTradeTicketTicketCodeCancelModel.setOrderNo(instance.getOrderNo());
            koubeiTradeTicketTicketCodeCancelModel.setCodeType(instance.getCodeType());
            return koubeiTradeTicketTicketCodeCancelModel;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
