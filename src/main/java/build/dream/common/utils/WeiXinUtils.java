package build.dream.common.utils;

import build.dream.common.beans.*;
import build.dream.common.constants.Constants;
import build.dream.common.models.weixin.*;
import build.dream.common.saas.domains.WeiXinAuthorizerInfo;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeiXinUtils {
    private static final String WEI_XIN_API_URL = "https://api.weixin.qq.com";
    private static final String COMPONENT_LOGIN_PAGE_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage";
    private static final String WEI_XIN_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    private static final Map<String, String> HEADERS = new HashMap<String, String>();

    static {
        HEADERS.put("Content-Type", "application/json;charset=UTF-8");
    }

    public static String generateComponentLoginPageUrl(String componentAppId, String preAuthCode, String redirectUri, String authType) {
        return COMPONENT_LOGIN_PAGE_URL + "?component_appid=" + componentAppId + "&pre_auth_code=" + preAuthCode + "&redirect_uri=" + redirectUri + "&auth_type=" + authType;
    }

    public static String generateAuthorizeUrl(String appId, String scope, String redirectUri, String state) throws IOException {
        return generateAuthorizeUrl(appId, scope, redirectUri, state, null);
    }

    public static String generateAuthorizeUrl(String appId, String scope, String redirectUri, String state, String componentAppId) throws IOException {
        if (StringUtils.isBlank(scope)) {
            scope = "snsapi_base";
        }
        StringBuilder authorizeUrl = new StringBuilder(WEI_XIN_AUTHORIZE_URL);
        authorizeUrl.append("?").append("appid=").append(appId);
        authorizeUrl.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, Constants.CHARSET_NAME_UTF_8));
        authorizeUrl.append("&response_type=code");
        authorizeUrl.append("&scope=").append(scope);
        authorizeUrl.append("&connect_redirect=1");
        if (StringUtils.isNotBlank(state)) {
            authorizeUrl.append("&state=");
            if (state.length() > 128) {
                authorizeUrl.append(state.substring(0, 128));
            } else {
                authorizeUrl.append(state);
            }
        }
        if (StringUtils.isNotBlank(componentAppId)) {
            authorizeUrl.append("&component_appid=").append(componentAppId);
        }
        authorizeUrl.append("&#wechat_redirect");
        return authorizeUrl.toString();
    }

    public static Map<String, String> generateJsApiConfig(String url, String appId, String appSecret) throws IOException {
        WeiXinJsapiTicket weiXinJsapiTicket = obtainJsapiTicket(appId, appSecret, Constants.WEI_XIN_TICKET_TYPE_JSAPI);

        String ticket = weiXinJsapiTicket.getTicket();
        String nonceStr = RandomStringUtils.randomAlphanumeric(32);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String str = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = DigestUtils.sha1Hex(str);

        Map<String, String> config = new HashMap<String, String>();
        config.put("appId", appId);
        config.put("timestamp", timestamp);
        config.put("nonceStr", nonceStr);
        config.put("url", url);
        config.put("signature", signature);
        return config;
    }

    public static WeiXinOAuthAccessToken obtainOAuthAccessToken(String appId, String secret, String code) {
        Map<String, String> obtainOAuthAccessTokenRequestParameters = new HashMap<String, String>();
        obtainOAuthAccessTokenRequestParameters.put("appid", appId);
        obtainOAuthAccessTokenRequestParameters.put("secret", secret);
        obtainOAuthAccessTokenRequestParameters.put("code", code);
        obtainOAuthAccessTokenRequestParameters.put("grant_type", "authorization_code");

        String url = WEI_XIN_API_URL + "/sns/oauth2/access_token";
        WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainOAuthAccessTokenRequestParameters);

        JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
        ValidateUtils.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

        WeiXinOAuthAccessToken weiXinOAuthAccessToken = new WeiXinOAuthAccessToken();
        weiXinOAuthAccessToken.setAccessToken(resultJsonObject.getString("access_token"));
        weiXinOAuthAccessToken.setExpiresIn(resultJsonObject.getInt("expires_in"));
        weiXinOAuthAccessToken.setRefreshToken(resultJsonObject.getString("refresh_token"));
        weiXinOAuthAccessToken.setOpenId(resultJsonObject.getString("openid"));
        weiXinOAuthAccessToken.setScope(resultJsonObject.getString("scope"));

        return weiXinOAuthAccessToken;
    }

    public static WeiXinOAuthAccessToken obtainOAuthAccessToken(String appId, String code, String componentAppId, String componentAccessToken) throws IOException {
        Map<String, String> obtainOAuthAccessTokenRequestParameters = new HashMap<String, String>();
        obtainOAuthAccessTokenRequestParameters.put("appid", appId);
        obtainOAuthAccessTokenRequestParameters.put("code", code);
        obtainOAuthAccessTokenRequestParameters.put("grant_type", "authorization_code");
        obtainOAuthAccessTokenRequestParameters.put("component_appid", componentAppId);
        obtainOAuthAccessTokenRequestParameters.put("component_access_token", componentAccessToken);

        String url = WEI_XIN_API_URL + "/sns/oauth2/component/access_token";
        WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainOAuthAccessTokenRequestParameters);

        JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
        ValidateUtils.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

        WeiXinOAuthAccessToken weiXinOAuthAccessToken = new WeiXinOAuthAccessToken();
        weiXinOAuthAccessToken.setAccessToken(resultJsonObject.getString("access_token"));
        weiXinOAuthAccessToken.setExpiresIn(resultJsonObject.getInt("expires_in"));
        weiXinOAuthAccessToken.setRefreshToken(resultJsonObject.getString("refresh_token"));
        weiXinOAuthAccessToken.setOpenId(resultJsonObject.getString("openid"));
        weiXinOAuthAccessToken.setScope(resultJsonObject.getString("scope"));

        return weiXinOAuthAccessToken;
    }

    public static WeiXinUserInfo obtainUserInfo(String accessToken, String openId, String lang) throws IOException {
        Map<String, String> obtainUserInfoRequestParameters = new HashMap<String, String>();
        obtainUserInfoRequestParameters.put("access_token", accessToken);
        obtainUserInfoRequestParameters.put("openid", openId);
        if (StringUtils.isNotBlank(lang)) {
            obtainUserInfoRequestParameters.put("lang", lang);
        }

        String url = WEI_XIN_API_URL + "/sns/userinfo";
        WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainUserInfoRequestParameters);
        JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
        if (resultJsonObject.has("errcode")) {
            ValidateUtils.isTrue(false, resultJsonObject.optString("errmsg"));
        }

        WeiXinUserInfo weiXinUserInfo = new WeiXinUserInfo();
        weiXinUserInfo.setOpenId(resultJsonObject.optString("openid"));
        weiXinUserInfo.setNickname(resultJsonObject.optString("nickname"));
        weiXinUserInfo.setSex(resultJsonObject.optInt("sex"));
        weiXinUserInfo.setProvince(resultJsonObject.optString("province"));
        weiXinUserInfo.setCity(resultJsonObject.optString("city"));
        weiXinUserInfo.setCountry(resultJsonObject.optString("country"));
        weiXinUserInfo.setHeadImgUrl(resultJsonObject.optString("headimgurl"));
        weiXinUserInfo.setPrivilege(StringUtils.join(resultJsonObject.optJSONArray("privilege"), ","));
        weiXinUserInfo.setUnionId(resultJsonObject.optString("unionid"));

        return weiXinUserInfo;
    }

    public static Map<String, Object> sendMassMessage(String accessToken, SendMassMessageModel sendMassMessageModel) throws IOException {
        String url = WEI_XIN_API_URL + "/message/mass/send?access_token=" + accessToken;
        WebResponse webResponse = WebUtils.doPostWithRequestBody(url, HEADERS, GsonUtils.toJson(sendMassMessageModel, false), null);
        Map<String, Object> resultMap = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        int errcode = MapUtils.getIntValue(resultMap, "errcode");
        ValidateUtils.isTrue(errcode == 0, MapUtils.getString(resultMap, "errmsg"));
        return resultMap;
    }

    public static WeiXinAccessToken obtainAccessToken(String appId, String secret) throws IOException {
        String weiXinAccessTokenJson = CacheUtils.hget(Constants.KEY_WEI_XIN_ACCESS_TOKENS, appId);
        boolean isRetrieveAccessToken = false;
        WeiXinAccessToken weiXinAccessToken = null;
        if (StringUtils.isNotBlank(weiXinAccessTokenJson)) {
            weiXinAccessToken = GsonUtils.fromJson(weiXinAccessTokenJson, WeiXinAccessToken.class);
            if ((System.currentTimeMillis() - weiXinAccessToken.getFetchTime().getTime()) / 1000 >= weiXinAccessToken.getExpiresIn()) {
                isRetrieveAccessToken = true;
            }
        } else {
            isRetrieveAccessToken = true;
        }
        if (isRetrieveAccessToken) {
            Map<String, String> obtainAccessTokenRequestParameters = new HashMap<String, String>();
            obtainAccessTokenRequestParameters.put("appid", appId);
            obtainAccessTokenRequestParameters.put("secret", secret);
            obtainAccessTokenRequestParameters.put("grant_type", "client_credential");
            String url = WEI_XIN_API_URL + "/cgi-bin/token";
            WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainAccessTokenRequestParameters);

            JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
            Validate.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

            weiXinAccessToken = new WeiXinAccessToken();
            weiXinAccessToken.setAccessToken(resultJsonObject.getString("access_token"));
            weiXinAccessToken.setExpiresIn(resultJsonObject.getInt("expires_in"));
            weiXinAccessToken.setFetchTime(new Date());
            CacheUtils.hset(Constants.KEY_WEI_XIN_ACCESS_TOKENS, appId, GsonUtils.toJson(weiXinAccessToken));
        }
        return weiXinAccessToken;
    }

    public static WeiXinJsapiTicket obtainJsapiTicket(String appId, String appSecret, String type) throws IOException {
        String weiXinJsapiTicketJson = CacheUtils.hget(Constants.KEY_WEI_XIN_JSAPI_TICKETS + "_" + type, appId);
        boolean isRetrieveJsapiTicket = false;
        WeiXinJsapiTicket weiXinJsapiTicket = null;
        if (StringUtils.isNotBlank(weiXinJsapiTicketJson)) {
            weiXinJsapiTicket = GsonUtils.fromJson(weiXinJsapiTicketJson, WeiXinJsapiTicket.class);
            if ((System.currentTimeMillis() - weiXinJsapiTicket.getFetchTime().getTime()) / 1000 >= weiXinJsapiTicket.getExpiresIn()) {
                isRetrieveJsapiTicket = true;
            }
        } else {
            isRetrieveJsapiTicket = true;
        }

        if (isRetrieveJsapiTicket) {
            WeiXinAccessToken weiXinAccessToken = obtainAccessToken(appId, appSecret);
            Map<String, String> obtainJsapiTicketRequestParameters = new HashMap<String, String>();
            obtainJsapiTicketRequestParameters.put("access_token", weiXinAccessToken.getAccessToken());
            obtainJsapiTicketRequestParameters.put("type", type);

            String url = WEI_XIN_API_URL + "/cgi-bin/ticket/getticket";
            WebResponse webResponse = WebUtils.doGetWithRequestParameters(url, obtainJsapiTicketRequestParameters);
            JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
            Validate.isTrue(resultJsonObject.optInt("errcode") == 0, resultJsonObject.optString("errmsg"));

            weiXinJsapiTicket = new WeiXinJsapiTicket();
            weiXinJsapiTicket.setTicket(resultJsonObject.optString("ticket"));
            weiXinJsapiTicket.setExpiresIn(resultJsonObject.optInt("expires_in"));
            weiXinJsapiTicket.setFetchTime(new Date());
            CacheUtils.hset(Constants.KEY_WEI_XIN_JSAPI_TICKETS + "_" + type, appId, GsonUtils.toJson(weiXinJsapiTicket));
        }
        return weiXinJsapiTicket;
    }

    public static Map<String, Object> createGrouponCoupon(BaseInfoModel baseInfoModel, AdvancedInfoModel advancedInfoModel, String dealDetail, String accessToken) {
        Map<String, Object> groupon = new HashMap<String, Object>();
        groupon.put("base_info", baseInfoModel);
        groupon.put("advanced_info", advancedInfoModel);
        groupon.put("deal_detail", dealDetail);

        Map<String, Object> card = new HashMap<String, Object>();
        card.put("card_type", "GROUPON");
        card.put("groupon", groupon);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("card", card);

        String url = WEI_XIN_API_URL + "/card/create?access_token=" + accessToken;
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(groupon, false));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        int errcode = MapUtils.getIntValue(result, "errcode");
        ValidateUtils.isTrue(errcode == 0, MapUtils.getString(result, "errmsg"));

        return result;
    }

    public static Map<String, Object> sendTemplateMessage(String openId, String templateId, String url, Map<String, Object> miniProgram, Map<String, Object> data, String color, String accessToken) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("touser", openId);
        body.put("template_id", templateId);
        body.put("url", url);
        if (MapUtils.isNotEmpty(miniProgram)) {
            body.put("miniprogram", miniProgram);
        }
        body.put("data", data);
        if (StringUtils.isNotBlank(color)) {
            body.put("data", color);
        }
        String _url = WEI_XIN_API_URL + "/cgi-bin/message/template/send?access_token=" + accessToken;
        WebResponse webResponse = OutUtils.doPostWithRequestBody(_url, null, GsonUtils.toJson(body));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        int errcode = MapUtils.getIntValue(result, "errcode");
        ValidateUtils.isTrue(errcode == 0, MapUtils.getString(result, "errmsg"));

        return result;
    }

    public static ComponentAccessToken obtainComponentAccessToken(String componentAppId, String componentAppSecret) {
        String componentAccessTokenJson = CacheUtils.hget(Constants.KEY_WEI_XIN_COMPONENT_ACCESS_TOKEN, componentAppId);
        boolean isRetrieveComponentAccessToken = false;

        ComponentAccessToken componentAccessToken = null;
        if (StringUtils.isNotBlank(componentAccessTokenJson)) {
            componentAccessToken = GsonUtils.fromJson(componentAccessTokenJson, ComponentAccessToken.class);
            if ((System.currentTimeMillis() - componentAccessToken.getFetchTime().getTime()) / 1000 >= componentAccessToken.getExpiresIn()) {
                isRetrieveComponentAccessToken = true;
            }
        } else {
            isRetrieveComponentAccessToken = true;
        }

        if (isRetrieveComponentAccessToken) {
            String componentVerifyTicket = CacheUtils.hget(Constants.KEY_WEI_XIN_COMPONENT_VERIFY_TICKET, componentAppId);
            ValidateUtils.notBlank(componentVerifyTicket, "component_verify_ticket 不存在！");
            String url = WEI_XIN_API_URL + "/cgi-bin/component/api_component_token";
            Map<String, Object> requestBody = new HashMap<String, Object>();
            requestBody.put("component_appid", componentAppId);
            requestBody.put("component_appsecret", componentAppSecret);
            requestBody.put("component_verify_ticket", componentVerifyTicket);
            WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
            Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
            componentAccessToken = new ComponentAccessToken();
            componentAccessToken.setComponentAccessToken(MapUtils.getString(result, "component_access_token"));
            componentAccessToken.setExpiresIn(MapUtils.getIntValue(result, "expires_in"));
            componentAccessToken.setFetchTime(new Date());
            CacheUtils.hset(Constants.KEY_WEI_XIN_COMPONENT_ACCESS_TOKEN, componentAppId, GsonUtils.toJson(componentAccessToken));
        }

        return componentAccessToken;
    }

    public static String obtainPreAuthCode(String componentAppId, String componentAppSecret) {
        ComponentAccessToken componentAccessToken = obtainComponentAccessToken(componentAppId, componentAppSecret);
        String url = WEI_XIN_API_URL + "/cgi-bin/component/api_create_preauthcode?component_access_token=" + componentAccessToken.getComponentAccessToken();
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        return MapUtils.getString(result, "pre_auth_code");
    }

    public static Map<String, Object> apiQueryAuth(String componentAccessToken, String componentAppId, String authorizationCode) {
        String url = WEI_XIN_API_URL + "/cgi-bin/component/api_query_auth?component_access_token=" + componentAccessToken;
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        requestBody.put("authorization_code", authorizationCode);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        return result;
    }

    public static Map<String, Object> createMenu(String accessToken, CreateMenuModel createMenuModel) {
        createMenuModel.validateAndThrow();
        String url = WEI_XIN_API_URL + "/cgi-bin/menu/create?access_token=" + accessToken;
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(createMenuModel, false));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        int errcode = MapUtils.getIntValue(result, "errcode");
        ValidateUtils.isTrue(errcode == 0, MapUtils.getString(result, "errmsg"));
        return result;
    }

    public static Map<String, Object> addPoi(String accessToken, AddPoiModel addPoiModel) {
        addPoiModel.validateAndThrow();
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("business", addPoiModel);

        String url = WEI_XIN_API_URL + "/cgi-bin/poi/addpoi?access_token=" + accessToken;
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody, false));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        int errcode = MapUtils.getIntValue(result, "errcode");
        ValidateUtils.isTrue(errcode == 0, MapUtils.getString(result, "errmsg"));
        return result;
    }

    public static WeiXinAuthorizerInfo apiGetAuthorizerInfo(String componentAccessToken, String componentAppId, String authorizerAppId) {
        String url = WEI_XIN_API_URL + "/component/api_get_authorizer_info?component_access_token=" + componentAccessToken;
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        requestBody.put("authorizer_appid", authorizerAppId);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        WeiXinAuthorizerInfo weiXinAuthorizerInfo = new WeiXinAuthorizerInfo();
        weiXinAuthorizerInfo.setNickName(MapUtils.getString(result, "nick_name"));
        weiXinAuthorizerInfo.setHeadImg(MapUtils.getString(result, "head_img"));
        weiXinAuthorizerInfo.setServiceTypeInfo(MapUtils.getIntValue(result, "service_type_info"));
        weiXinAuthorizerInfo.setVerifyTypeInfo(MapUtils.getIntValue(result, "verify_type_info"));
        weiXinAuthorizerInfo.setOriginalId(MapUtils.getString(result, "user_name"));
        weiXinAuthorizerInfo.setPrincipalName(MapUtils.getString(result, "principal_name"));
        String alias = MapUtils.getString(result, "alias");
        weiXinAuthorizerInfo.setAlias(StringUtils.isNotBlank(alias) ? alias : Constants.VARCHAR_DEFAULT_VALUE);
        weiXinAuthorizerInfo.setBusinessInfo(MapUtils.getString(result, "business_info"));
        weiXinAuthorizerInfo.setQrcodeUrl(MapUtils.getString(result, "qrcode_url"));

        Map<String, Object> authorizationInfo = MapUtils.getMap(result, "authorization_info");
        weiXinAuthorizerInfo.setAuthorizationAppId(MapUtils.getString(authorizationInfo, "authorization_appid"));
        weiXinAuthorizerInfo.setFuncInfo(MapUtils.getString(authorizationInfo, "func_info"));
        return weiXinAuthorizerInfo;
    }

    public static Map<String, Object> apiAuthorizerToken(String componentAccessToken, String componentAppId, String authorizerAppId, String authorizerRefreshToken) {
        String url = WEI_XIN_API_URL + "/cgi-bin/component/api_authorizer_token?component_access_token=" + componentAccessToken;
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        requestBody.put("authorizer_appid", authorizerAppId);
        requestBody.put("authorizer_refresh_token", authorizerRefreshToken);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
        return JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
    }
}
