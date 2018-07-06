package build.dream.common.utils;

import build.dream.common.api.ApiRest;
import build.dream.common.constants.Constants;
import build.dream.common.models.weixin.MicroPayModel;
import build.dream.common.models.weixin.UnifiedOrderModel;
import build.dream.common.saas.domains.WeiXinPayAccount;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WeiXinPayUtils {
    private static WeiXinPayAccount obtainWeiXinPayAccount(String tenantId, String branchId) {
        return null;
    }

    public static String generateSign(Map<String, String> callWeiXinSystemRequestParameters, String weiXinPayKey, String signType) {
        Map<String, String> sortedCallWeiXinSystemRequestParameters = null;
        if (callWeiXinSystemRequestParameters instanceof TreeMap) {
            sortedCallWeiXinSystemRequestParameters = callWeiXinSystemRequestParameters;
        } else {
            sortedCallWeiXinSystemRequestParameters = new TreeMap<String, String>(callWeiXinSystemRequestParameters);
        }
        Set<Map.Entry<String, String>> entries = sortedCallWeiXinSystemRequestParameters.entrySet();
        StringBuilder stringSignTemp = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                stringSignTemp.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        stringSignTemp.append("key=");
        stringSignTemp.append(weiXinPayKey);
        String sign = null;
        if (Constants.MD5.equals(signType)) {
            sign = DigestUtils.md5Hex(stringSignTemp.toString()).toUpperCase();
        } else if (Constants.HMAC_SHA256.equals(signType)) {
            sign = HmacUtils.hmacSha256Hex(weiXinPayKey, stringSignTemp.toString()).toUpperCase();
        }
        return sign;
    }

    public static boolean checkSign(Map<String, String> weiXinSystemResult, String weiXinPayKey, String signType) {
        Map<String, String> sortedWeiXinSystemResult = new TreeMap<String, String>();
        sortedWeiXinSystemResult.putAll(weiXinSystemResult);
        String sign = sortedWeiXinSystemResult.remove("sign");
        return sign.equals(generateSign(sortedWeiXinSystemResult, weiXinPayKey, signType));
    }

    public static String generateFinalData(Map<String, String> callWeiXinSystemRequestParameters) {
        StringBuffer weiXinPayFinalData = new StringBuffer();
        weiXinPayFinalData.append("<xml>");
        Set<Map.Entry<String, String>> entries = callWeiXinSystemRequestParameters.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            weiXinPayFinalData.append("<").append(key).append(">").append(String.format(Constants.CDATA_FORMAT, entry.getValue())).append("</").append(key).append(">");
        }

        weiXinPayFinalData.append("</xml>");
        return weiXinPayFinalData.toString();
    }

    public static Map<String, String> callWeiXinPaySystem(String url, String finalData, String certificate, String password) throws IOException, DocumentException {
        String result = OutUtils.doPost(url, finalData, null, certificate, password);
        return WebUtils.xmlStringToMap(result);
    }

    public static Map<String, String> callWeiXinPaySystem(String url, String finalData) throws IOException, DocumentException {
        return callWeiXinPaySystem(url, finalData, null, null);
    }

    public static Map<String, String> microPay(String tenantId, String branchId, MicroPayModel microPayModel) throws IOException, DocumentException {
        WeiXinPayAccount weiXinPayAccount = obtainWeiXinPayAccount(tenantId, branchId);
        ValidateUtils.notNull(weiXinPayAccount, "未配置微信支付账号！");

        Map<String, String> microPayRequestParameters = new HashMap<String, String>();
        microPayRequestParameters.put("appid", weiXinPayAccount.getAppId());
        microPayRequestParameters.put("mch_id", weiXinPayAccount.getMchId());

        if (weiXinPayAccount.isAcceptanceModel()) {
            String subAppId = weiXinPayAccount.getSubPublicAccountAppId();
            ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "sub_appid", subAppId);
            microPayRequestParameters.put("sub_mch_id", weiXinPayAccount.getSubMchId());

        }

        microPayRequestParameters.put("mch_id", weiXinPayAccount.getMchId());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "device_info", microPayModel.getDeviceInfo());
        microPayRequestParameters.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));

        String signType = microPayModel.getSignType();
        microPayRequestParameters.put("sign_type", signType);

        microPayRequestParameters.put("body", microPayModel.getBody());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "detail", microPayModel.getDetail());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "attach", microPayModel.getAttach());
        microPayRequestParameters.put("out_trade_no", microPayModel.getOutTradeNo());
        microPayRequestParameters.put("total_fee", microPayModel.getTotalFee().toString());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "fee_type", microPayModel.getFeeType());
        microPayRequestParameters.put("spbill_create_ip", microPayModel.getSpbillCreateIp());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "goods_tag", microPayModel.getGoodsTag());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "limit_pay", microPayModel.getLimitPay());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "time_start", microPayModel.getTimeStart());
        ApplicationHandler.ifNotBlankPut(microPayRequestParameters, "time_expire", microPayModel.getTimeExpire());
        microPayRequestParameters.put("auth_code", microPayModel.getAuthCode());
        if (microPayModel.getSceneInfoModel() != null) {
            microPayRequestParameters.put("scene_info", GsonUtils.toJson(microPayModel.getSceneInfoModel(), false));
        }

        String sign = generateSign(microPayRequestParameters, weiXinPayAccount.getApiSecretKey(), Constants.MD5);
        microPayRequestParameters.put("sign", sign);

        String microPayFinalData = generateFinalData(microPayRequestParameters);
        Map<String, String> microPayResult = callWeiXinPaySystem(ConfigurationUtils.getConfiguration(Constants.WEI_XIN_PAY_API_URL) + Constants.WEI_XIN_PAY_MICRO_PAY_URI, microPayFinalData);

        String returnCode = microPayResult.get("return_code");
        Validate.isTrue(Constants.SUCCESS.equals(returnCode), microPayResult.get("return_msg"));

        Validate.isTrue(checkSign(microPayResult, weiXinPayAccount.getApiSecretKey(), Constants.MD5), "微信系统返回结果签名校验未通过！");

        String resultCode = microPayResult.get("result_code");
        String errCode = microPayResult.get("err_code");
        Validate.isTrue((Constants.SUCCESS.equals(resultCode) || (Constants.FAIL.equals(resultCode) && "USERPAYING".equals(errCode))), microPayResult.get("err_code_des"));

        return microPayResult;
    }

    public static Map<String, String> unifiedOrder(String tenantId, String branchId, UnifiedOrderModel unifiedOrderModel) throws IOException, DocumentException {
        WeiXinPayAccount weiXinPayAccount = obtainWeiXinPayAccount(tenantId, branchId);
        ValidateUtils.notNull(weiXinPayAccount, "未配置微信支付账号！");

        Map<String, String> unifiedOrderRequestParameters = new HashMap<String, String>();
        unifiedOrderRequestParameters.put("appid", weiXinPayAccount.getAppId());
        unifiedOrderRequestParameters.put("mch_id", weiXinPayAccount.getMchId());

        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "device_info", unifiedOrderModel.getDeviceInfo());
        unifiedOrderRequestParameters.put("nonce_str", RandomStringUtils.randomAlphanumeric(32));

        String signType = unifiedOrderModel.getSignType();
        unifiedOrderRequestParameters.put("sign_type", signType);

        unifiedOrderRequestParameters.put("body", unifiedOrderModel.getBody());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "detail", unifiedOrderModel.getDetail());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "attach", unifiedOrderModel.getAttach());

        String outTradeNo = unifiedOrderModel.getOutTradeNo();
        unifiedOrderRequestParameters.put("out_trade_no", outTradeNo);

        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "fee_type", unifiedOrderModel.getFeeType());
        unifiedOrderRequestParameters.put("total_fee", unifiedOrderModel.getTotalFee().toString());
        unifiedOrderRequestParameters.put("spbill_create_ip", unifiedOrderModel.getSpbillCreateIp());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "time_start", unifiedOrderModel.getTimeStart());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "time_expire", unifiedOrderModel.getTimeExpire());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "goods_tag", unifiedOrderModel.getGoodsTag());

        String notifyUrl = unifiedOrderModel.getNotifyUrl();
        unifiedOrderRequestParameters.put("notify_url", notifyUrl);

        String tradeType = unifiedOrderModel.getTradeType();
        unifiedOrderRequestParameters.put("trade_type", tradeType);

        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "product_id", unifiedOrderModel.getProductId());
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "limit_pay", unifiedOrderModel.getLimitPay());

        if (Constants.WEI_XIN_PAY_TRADE_TYPE_JSAPI.equals(tradeType)) {
            String openId = unifiedOrderModel.getOpenId();
            if (weiXinPayAccount.isAcceptanceModel()) {
                String subOpenId = unifiedOrderModel.getSubOpenId();
                ApplicationHandler.isTrue(StringUtils.isNotBlank(openId) || StringUtils.isNotBlank(subOpenId), "参数openId和subOpenId不能同时为空！");
                if (StringUtils.isNotBlank(subOpenId)) {
                    ValidateUtils.notBlank(weiXinPayAccount.getSubPublicAccountAppId(), "支付账号未配置子商户公众账号！");
                }

                ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "sub_appid", weiXinPayAccount.getSubPublicAccountAppId());
                unifiedOrderRequestParameters.put("sub_mch_id", weiXinPayAccount.getSubMchId());
                ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "openid", openId);
                ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "sub_openid", subOpenId);
            } else {
                ApplicationHandler.notBlank(openId, "openId");
                unifiedOrderRequestParameters.put("openid", openId);
            }
        }
        ApplicationHandler.ifNotBlankPut(unifiedOrderRequestParameters, "openid", unifiedOrderModel.getOpenId());

        UnifiedOrderModel.SceneInfoModel sceneInfoModel = unifiedOrderModel.getSceneInfoModel();
        if (sceneInfoModel != null) {
            unifiedOrderRequestParameters.put("scene_info", GsonUtils.toJson(sceneInfoModel, false));
        }

        String apiSecretKey = weiXinPayAccount.getApiSecretKey();
        String sign = generateSign(unifiedOrderRequestParameters, apiSecretKey, signType);
        unifiedOrderRequestParameters.put("sign", sign);

        String unifiedOrderFinalData = generateFinalData(unifiedOrderRequestParameters);
        Map<String, String> unifiedOrderResult = callWeiXinPaySystem(ConfigurationUtils.getConfiguration(Constants.WEI_XIN_PAY_API_URL) + Constants.WEI_XIN_PAY_MICRO_PAY_URI, unifiedOrderFinalData);

        String returnCode = unifiedOrderResult.get("return_code");
        Validate.isTrue(Constants.SUCCESS.equals(returnCode), unifiedOrderResult.get("return_msg"));

        Validate.isTrue(checkSign(unifiedOrderResult, weiXinPayAccount.getApiSecretKey(), Constants.MD5), "微信系统返回结果签名校验未通过！");
        String resultCode = unifiedOrderResult.get("result_code");

        Validate.isTrue(Constants.SUCCESS.equals(resultCode), unifiedOrderResult.get("err_code_des"));

        // 保存异步通知记录
        saveNotifyRecord(outTradeNo, notifyUrl, apiSecretKey, signType);

        Map<String, String> data = new HashMap<String, String>();
        if (Constants.WEI_XIN_PAY_TRADE_TYPE_APP.equals(tradeType)) {
            if (weiXinPayAccount.isAcceptanceModel()) {
                data.put("appid", unifiedOrderResult.get("sub_appid"));
                data.put("partnerid", unifiedOrderResult.get("sub_mch_id"));
            } else {
                data.put("appid", unifiedOrderResult.get("appid"));
                data.put("partnerid", unifiedOrderResult.get("mch_id"));
            }
            data.put("prepayid", unifiedOrderResult.get("prepay_id"));
            data.put("package", "Sign=WXPay");
            data.put("noncestr", RandomStringUtils.randomAlphanumeric(32));
            data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            data.put("sign", generateSign(data, weiXinPayAccount.getApiSecretKey(), Constants.MD5));
        } else if (Constants.WEI_XIN_PAY_TRADE_TYPE_MWEB.equals(tradeType)) {
            data.put("mwebUrl", unifiedOrderResult.get("mweb_url"));
        } else if (Constants.WEI_XIN_PAY_TRADE_TYPE_NATIVE.equals(tradeType)) {
            data.put("codeUrl", unifiedOrderResult.get("code_url"));
        } else if (Constants.WEI_XIN_PAY_TRADE_TYPE_JSAPI.equals(tradeType)) {
            data.put("appId", unifiedOrderResult.get("appid"));
            data.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            data.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
            data.put("package", "prepay_id=" + unifiedOrderResult.get("prepay_id"));
            data.put("signType", signType);
            data.put("paySign", generateSign(data, weiXinPayAccount.getApiSecretKey(), signType));
        } else if (Constants.WEI_XIN_PAY_TRADE_TYPE_MINI_PROGRAM.equals(tradeType)) {
            if (weiXinPayAccount.isAcceptanceModel()) {
                data.put("appId", unifiedOrderResult.get("sub_appid"));
            } else {
                data.put("appId", unifiedOrderResult.get("appid"));
            }
            data.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            data.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
            data.put("package", "prepay_id=" + unifiedOrderResult.get("prepay_id"));
            data.put("signType", signType);
            data.put("paySign", generateSign(data, weiXinPayAccount.getApiSecretKey(), signType));
        }

        return data;
    }

    private static void saveNotifyRecord(String uuid, String notifyUrl, String weiXinPayApiSecretKey, String weiXinPaySignType) throws IOException {
        Map<String, String> saveNotifyRecordRequestParameters = new HashMap<String, String>();
        saveNotifyRecordRequestParameters.put("uuid", uuid);
        saveNotifyRecordRequestParameters.put("notifyUrl", notifyUrl);
        saveNotifyRecordRequestParameters.put("weiXinPayApiSecretKey", weiXinPayApiSecretKey);
        saveNotifyRecordRequestParameters.put("weiXinPaySignType", weiXinPaySignType);

        ApiRest saveNotifyRecordResult = ProxyUtils.doPostWithRequestParameters(Constants.SERVICE_NAME_PLATFORM, "notify", "saveNotifyRecord", saveNotifyRecordRequestParameters);
        ValidateUtils.isTrue(saveNotifyRecordResult.isSuccessful(), saveNotifyRecordResult.getError());
    }
}
