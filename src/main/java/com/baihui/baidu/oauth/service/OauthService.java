package com.baihui.baidu.oauth.service;

import com.baihui.studio.service.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 百度开放认证
 *
 * @author xiayouxue
 * @date 2014/4/2 16:23
 */
@Service
public class OauthService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${baidu.authorizationcode}")
    private String authorizationCode;
    @Value(value = "${baidu.authorizationcode.url}")
    private String codeUrl;
    @Value(value = "${baidu.authorizationcode.client_id}")
    private String codeClientId;
    @Value(value = "${baidu.authorizationcode.response_type}")
    private String codeResponseType;
    @Value(value = "${baidu.authorizationcode.redirect_uri}")
    private String codeRedirectUri;
    @Value(value = "${baidu.authorizationcode.redirect_uri.value}")
    private String codeValueRedirectUri;

    @Value(value = "${baidu.accesstoken}")
    private String accessToken;
    @Value(value = "${baidu.accesstoken.url}")
    private String tokenUrl;
    @Value(value = "${baidu.accesstoken.grant_type}")
    private String tokenGrantType;
    @Value(value = "${baidu.accesstoken.client_id}")
    private String tokenClientId;
    @Value(value = "${baidu.accesstoken.client_secret}")
    private String tokenClientSecret;
    @Value(value = "${baidu.accesstoken.redirect_uri}")
    private String tokenRedirectUri;

    public String getAuthorizationCodeUrl(String redirectUri) {
        return String.format(authorizationCode, redirectUri);
    }

    public String getAuthorizationCode(String authorizationCodeUrl) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(authorizationCodeUrl);
        httpClient.executeMethod(postMethod);
        String code = IOUtils.toString(postMethod.getResponseBodyAsStream());
        logger.info("AuthorizationCode:{}", code);
        postMethod.releaseConnection();
        return code;
    }

    public String getAccessTokenUrl(String code, String redirectUri) {
        return String.format(accessToken, code, redirectUri);
    }

    public Map<String, String> getAccessToken(String tokenUrl) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(tokenUrl);
        httpClient.executeMethod(postMethod);
        String accessToken = IOUtils.toString(postMethod.getResponseBodyAsStream());
        logger.info("AccessToken:{}", accessToken);
        postMethod.releaseConnection();

        ObjectMapper mapper = new ObjectMapper();
        Map accessTokenMap = mapper.readValue(accessToken, Map.class);
        if (accessTokenMap.containsKey("error")) {
            logger.error("获取访问令牌异常！");
            throw new ServiceException(accessToken);
        }
        return accessTokenMap;
    }

    public String getTokenRedirectUri() {
        return tokenRedirectUri;
    }

    public void setTokenRedirectUri(String tokenRedirectUri) {
        this.tokenRedirectUri = tokenRedirectUri;
    }

    public String getCodeRedirectUri() {
        return codeRedirectUri;
    }

    public void setCodeRedirectUri(String codeRedirectUri) {
        this.codeRedirectUri = codeRedirectUri;
    }

    public String getCodeValueRedirectUri() {
        return codeValueRedirectUri;
    }

    public void setCodeValueRedirectUri(String codeValueRedirectUri) {
        this.codeValueRedirectUri = codeValueRedirectUri;
    }
}
