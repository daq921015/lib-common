package build.dream.common.models.weixinpay;

import build.dream.common.models.BasicModel;
import build.dream.common.utils.ApplicationHandler;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class MicroPayModel extends BasicModel {
    private static final String[] SIGN_TYPES = {"MD5", "HMAC-SHA256"};
    private static final String[] FEE_TYPES = {"CNY"};
    private static final String[] LIMIT_PAYS = {"no_credit"};
    @Length(max = 32)
    private String deviceInfo;

    private String signType;

    @NotNull
    @Length(max = 128)
    private String body;

    @Length(max = 6000)
    private String detail;

    @Length(max = 127)
    private String attach;

    @NotNull
    @Length(max = 32)
    private String outTradeNo;

    @NotNull
    private Integer totalFee;

    private String feeType;

    @NotNull
    @Length(max = 16)
    private String spbillCreateIp;

    @Length(max = 32)
    private String goodsTag;

    private String limitPay;

    @Length(min = 14, max = 14)
    private String timeStart;

    @Length(min = 14, max = 14)
    private String timeExpire;

    @NotNull
    @Length(max = 128)
    private String authCode;

    private SceneInfoModel sceneInfoModel;

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public SceneInfoModel getSceneInfoModel() {
        return sceneInfoModel;
    }

    @Override
    public boolean validate() {
        return super.validate() && ArrayUtils.contains(SIGN_TYPES, signType) && (StringUtils.isNotBlank(feeType) ? ArrayUtils.contains(FEE_TYPES, feeType) : true) && (StringUtils.isNotBlank(limitPay) ? ArrayUtils.contains(LIMIT_PAYS, limitPay) : true);
    }

    @Override
    public void validateAndThrow() {
        super.validateAndThrow();
        ApplicationHandler.inArray(SIGN_TYPES, signType, "signType");
        if (StringUtils.isNotBlank(feeType)) {
            ApplicationHandler.inArray(FEE_TYPES, feeType, "feeType");
        }
        if (StringUtils.isNotBlank(limitPay)) {
            ApplicationHandler.inArray(LIMIT_PAYS, limitPay, "limitPay");
        }
    }

    public void setSceneInfoModel(SceneInfoModel sceneInfoModel) {
        this.sceneInfoModel = sceneInfoModel;
    }

    public static class SceneInfoModel extends BasicModel {
        @SerializedName(value = "store_info", alternate = "storeInfoModel")
        private StoreInfoModel storeInfoModel;

        public StoreInfoModel getStoreInfoModel() {
            return storeInfoModel;
        }

        public void setStoreInfoModel(StoreInfoModel storeInfoModel) {
            this.storeInfoModel = storeInfoModel;
        }
    }

    public static class StoreInfoModel extends BasicModel {
        @Length(max = 32)
        private String id;

        @Length(max = 64)
        private String name;

        @Length(max = 6)
        @SerializedName(value = "area_code", alternate = "areaCode")
        private String areaCode;

        @Length(max = 128)
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
