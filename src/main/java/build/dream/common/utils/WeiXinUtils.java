package build.dream.common.utils;

import build.dream.common.api.ApiRest;
import build.dream.common.beans.*;
import build.dream.common.constants.Constants;
import build.dream.common.models.weixin.*;
import build.dream.common.saas.domains.WeiXinAuthorizerInfo;
import build.dream.common.saas.domains.WeiXinAuthorizerToken;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

    public static String generateComponentLoginPageUrl(String componentAppId, String preAuthCode, String redirectUri, String authType) throws UnsupportedEncodingException {
        return COMPONENT_LOGIN_PAGE_URL + "?component_appid=" + componentAppId + "&pre_auth_code=" + preAuthCode + "&redirect_uri=" + URLEncoder.encode(redirectUri, Constants.CHARSET_NAME_UTF_8) + "&auth_type=" + authType;
    }

    public static String generateAuthorizeUrl(String appId, String scope, String redirectUri, String state) throws IOException {
        return generateAuthorizeUrl(appId, scope, redirectUri, state, null);
    }

    public static String generateAuthorizeUrl(String appId, String scope, String redirectUri, String state, String componentAppId) throws IOException {
        if (StringUtils.isBlank(scope)) {
            scope = Constants.SNSAPI_BASE;
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

    public static WeiXinOAuthToken obtainOAuthToken(String appId, String secret, String code) {
        Map<String, String> obtainOAuthTokenRequestParameters = new HashMap<String, String>();
        obtainOAuthTokenRequestParameters.put("appid", appId);
        obtainOAuthTokenRequestParameters.put("secret", secret);
        obtainOAuthTokenRequestParameters.put("code", code);
        obtainOAuthTokenRequestParameters.put("grant_type", "authorization_code");

        String url = WEI_XIN_API_URL + "/sns/oauth2/access_token";
        WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainOAuthTokenRequestParameters);

        JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
        ValidateUtils.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

        WeiXinOAuthToken weiXinOAuthToken = new WeiXinOAuthToken();
        weiXinOAuthToken.setAccessToken(resultJsonObject.getString("access_token"));
        weiXinOAuthToken.setExpiresIn(resultJsonObject.getInt("expires_in"));
        weiXinOAuthToken.setRefreshToken(resultJsonObject.getString("refresh_token"));
        weiXinOAuthToken.setOpenId(resultJsonObject.getString("openid"));
        weiXinOAuthToken.setScope(resultJsonObject.getString("scope"));

        return weiXinOAuthToken;
    }

    public static WeiXinOAuthToken obtainOAuthToken(String appId, String code, String componentAppId, String componentAccessToken) {
        Map<String, String> obtainOAuthTokenRequestParameters = new HashMap<String, String>();
        obtainOAuthTokenRequestParameters.put("appid", appId);
        obtainOAuthTokenRequestParameters.put("code", code);
        obtainOAuthTokenRequestParameters.put("grant_type", "authorization_code");
        obtainOAuthTokenRequestParameters.put("component_appid", componentAppId);
        obtainOAuthTokenRequestParameters.put("component_access_token", componentAccessToken);

        String url = WEI_XIN_API_URL + "/sns/oauth2/component/access_token";
        WebResponse webResponse = OutUtils.doGetWithRequestParameters(url, null, obtainOAuthTokenRequestParameters);

        JSONObject resultJsonObject = JSONObject.fromObject(webResponse.getResult());
        ValidateUtils.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

        WeiXinOAuthToken weiXinOAuthToken = new WeiXinOAuthToken();
        weiXinOAuthToken.setAccessToken(resultJsonObject.getString("access_token"));
        weiXinOAuthToken.setExpiresIn(resultJsonObject.getInt("expires_in"));
        weiXinOAuthToken.setRefreshToken(resultJsonObject.getString("refresh_token"));
        weiXinOAuthToken.setOpenId(resultJsonObject.getString("openid"));
        weiXinOAuthToken.setScope(resultJsonObject.getString("scope"));

        return weiXinOAuthToken;
    }

    public static WeiXinUserInfo obtainUserInfoByThirdParty(String authorizerAccessToken, String openId, String lang) {
        return obtainUserInfo(authorizerAccessToken, openId, lang, Constants.IDENTITY_TYPE_THIRD_PARTY_APPLICATION);
    }

    public static WeiXinUserInfo obtainUserInfo(String accessToken, String openId, String lang) {
        return obtainUserInfo(accessToken, openId, lang, Constants.IDENTITY_TYPE_PUBLIC_ACCOUNT);
    }

    public static WeiXinUserInfo obtainUserInfo(String token, String openId, String lang, int type) {
        Map<String, String> obtainUserInfoRequestParameters = new HashMap<String, String>();
        if (type == Constants.IDENTITY_TYPE_PUBLIC_ACCOUNT) {
            obtainUserInfoRequestParameters.put("access_token", token);
        } else if (type == Constants.IDENTITY_TYPE_THIRD_PARTY_APPLICATION) {
            obtainUserInfoRequestParameters.put("authorizer_access_token", token);
        }
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

    public static WeiXinAccessToken obtainAccessToken(String appId, String secret) {
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
            ValidateUtils.isTrue(!resultJsonObject.has("errcode"), resultJsonObject.optString("errmsg"));

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
            ValidateUtils.isTrue(resultJsonObject.optInt("errcode") == 0, resultJsonObject.optString("errmsg"));

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
            ValidateUtils.isTrue(!result.containsKey("errcode"), MapUtils.getString(result, "errmsg"));

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

        ValidateUtils.isTrue(!result.containsKey("errcode"), MapUtils.getString(result, "errmsg"));
        return MapUtils.getString(result, "pre_auth_code");
    }

    public static WeiXinAuthorizerToken apiQueryAuth(String componentAccessToken, String componentAppId, String authorizationCode) throws IOException {
        String url = WEI_XIN_API_URL + "/cgi-bin/component/api_query_auth?component_access_token=" + componentAccessToken;
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        requestBody.put("authorization_code", authorizationCode);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));
        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        ValidateUtils.isTrue(!result.containsKey("errcode"), MapUtils.getString(result, "errmsg"));

        Map<String, Object> authorizationInfo = MapUtils.getMap(result, "authorization_info");
        String authorizerAppId = MapUtils.getString(authorizationInfo, "authorizer_appid");
        String authorizerAccessToken = MapUtils.getString(authorizationInfo, "authorizer_appid");
        int expiresIn = MapUtils.getIntValue(authorizationInfo, "expires_in");
        String authorizerRefreshToken = MapUtils.getString(authorizationInfo, "authorizer_refresh_token");
        Date fetchTime = new Date();

        WeiXinAuthorizerToken weiXinAuthorizerToken = new WeiXinAuthorizerToken();
        weiXinAuthorizerToken.setComponentAppId(componentAppId);
        weiXinAuthorizerToken.setAuthorizerAppId(authorizerAppId);
        weiXinAuthorizerToken.setAuthorizerAccessToken(authorizerAccessToken);
        weiXinAuthorizerToken.setExpiresIn(expiresIn);
        weiXinAuthorizerToken.setAuthorizerRefreshToken(authorizerRefreshToken);
        weiXinAuthorizerToken.setFetchTime(fetchTime);

        Map<String, String> saveWeiXinAuthorizerTokenRequestParameters = new HashMap<String, String>();
        saveWeiXinAuthorizerTokenRequestParameters.put("componentAppId", componentAppId);
        saveWeiXinAuthorizerTokenRequestParameters.put("authorizerAppId", authorizerAppId);
        saveWeiXinAuthorizerTokenRequestParameters.put("authorizerAccessToken", authorizerAccessToken);
        saveWeiXinAuthorizerTokenRequestParameters.put("expiresIn", String.valueOf(expiresIn));
        saveWeiXinAuthorizerTokenRequestParameters.put("authorizerRefreshToken", authorizerRefreshToken);
        saveWeiXinAuthorizerTokenRequestParameters.put("fetchTime", new SimpleDateFormat(Constants.DEFAULT_DATE_PATTERN).format(fetchTime));
        ApiRest apiRest = ProxyUtils.doPostWithRequestParameters(Constants.SERVICE_NAME_PLATFORM, "weiXin", "saveWeiXinAuthorizerToken", saveWeiXinAuthorizerTokenRequestParameters);
        ValidateUtils.isTrue(apiRest.isSuccessful(), apiRest.getError());
        CacheUtils.hset(Constants.KEY_WEI_XIN_AUTHORIZER_TOKENS, componentAppId + "_" + authorizerAppId, GsonUtils.toJson(weiXinAuthorizerToken));
        return weiXinAuthorizerToken;
    }

    public static WeiXinAuthorizerToken obtainWeiXinAuthorizerToken(String componentAppId, String authorizerAppId) {
        String tokenJson = CacheUtils.hget(Constants.SERVICE_NAME_PLATFORM, componentAppId + "_" + authorizerAppId);
        ValidateUtils.notBlank(tokenJson, "授权信息不存在！");
        return GsonUtils.fromJson(tokenJson, WeiXinAuthorizerToken.class);
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
        ValidateUtils.isTrue(!result.containsKey("errcode"), MapUtils.getString(result, "errmsg"));

        Map<String, Object> authorizerInfo = MapUtils.getMap(result, "authorizer_info");

        WeiXinAuthorizerInfo weiXinAuthorizerInfo = new WeiXinAuthorizerInfo();
        weiXinAuthorizerInfo.setNickName(MapUtils.getString(authorizerInfo, "nick_name"));
        weiXinAuthorizerInfo.setHeadImg(MapUtils.getString(authorizerInfo, "head_img"));
        weiXinAuthorizerInfo.setServiceTypeInfo(MapUtils.getString(authorizerInfo, "service_type_info"));
        weiXinAuthorizerInfo.setVerifyTypeInfo(MapUtils.getString(authorizerInfo, "verify_type_info"));
        weiXinAuthorizerInfo.setOriginalId(MapUtils.getString(authorizerInfo, "user_name"));
        weiXinAuthorizerInfo.setPrincipalName(MapUtils.getString(authorizerInfo, "principal_name"));
        String alias = MapUtils.getString(authorizerInfo, "alias");
        weiXinAuthorizerInfo.setAlias(StringUtils.isNotBlank(alias) ? alias : Constants.VARCHAR_DEFAULT_VALUE);
        weiXinAuthorizerInfo.setBusinessInfo(MapUtils.getString(authorizerInfo, "business_info"));
        weiXinAuthorizerInfo.setQrcodeUrl(MapUtils.getString(authorizerInfo, "qrcode_url"));
        weiXinAuthorizerInfo.setSignature(MapUtils.getString(authorizerInfo, "signature"));

        String miniProgramInfo = MapUtils.getString(authorizerInfo, "MiniProgramInfo");
        if (StringUtils.isBlank(miniProgramInfo)) {
            weiXinAuthorizerInfo.setAuthorizerType(Constants.AUTHORIZER_TYPE_PUBLIC_ACCOUNT);
        } else {
            weiXinAuthorizerInfo.setAuthorizerType(Constants.AUTHORIZER_TYPE_MINI_PROGRAM);
        }
        weiXinAuthorizerInfo.setMiniProgramInfo(miniProgramInfo);

        Map<String, Object> authorizationInfo = MapUtils.getMap(result, "authorization_info");
        weiXinAuthorizerInfo.setAuthorizationAppId(MapUtils.getString(authorizationInfo, "authorization_appid"));
        weiXinAuthorizerInfo.setFuncInfo(MapUtils.getString(authorizationInfo, "func_info"));
        return weiXinAuthorizerInfo;
    }

    public static WeiXinAuthorizerToken apiAuthorizerToken(String componentAccessToken, String componentAppId, String authorizerAppId, String authorizerRefreshToken) {
        String url = WEI_XIN_API_URL + "/cgi-bin/component/api_authorizer_token?component_access_token=" + componentAccessToken;
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("component_appid", componentAppId);
        requestBody.put("authorizer_appid", authorizerAppId);
        requestBody.put("authorizer_refresh_token", authorizerRefreshToken);
        WebResponse webResponse = OutUtils.doPostWithRequestBody(url, null, GsonUtils.toJson(requestBody));

        Map<String, Object> result = JacksonUtils.readValueAsMap(webResponse.getResult(), String.class, Object.class);
        ValidateUtils.isTrue(!result.containsKey("errcode"), MapUtils.getString(result, "errmsg"));

        WeiXinAuthorizerToken weiXinAuthorizerToken = new WeiXinAuthorizerToken();
        weiXinAuthorizerToken.setComponentAppId(componentAppId);
        weiXinAuthorizerToken.setAuthorizerAppId(authorizerAppId);
        weiXinAuthorizerToken.setAuthorizerAccessToken(MapUtils.getString(result, "authorizer_access_token"));
        weiXinAuthorizerToken.setExpiresIn(MapUtils.getIntValue(result, "expires_in"));
        weiXinAuthorizerToken.setAuthorizerRefreshToken(MapUtils.getString(result, "authorizer_refresh_token"));
        weiXinAuthorizerToken.setFetchTime(new Date());
        return weiXinAuthorizerToken;
    }
}
