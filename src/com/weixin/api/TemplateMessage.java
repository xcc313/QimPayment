package com.weixin.api;

import com.framework.utils.Logger;
import net.sf.json.JSONObject;

public class TemplateMessage extends WeixinAPI {
    private static final String SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    public TemplateMessage(String accessToken) {
        accessToken_ = accessToken;
    }

    @Override
    protected String getAPIUri() {
        return String.format(SEND_MESSAGE, accessToken_);
    }

    @Override
    protected boolean handlerResponse(String responseResult) throws Exception {
        JSONObject jsonParse = JSONObject.fromObject(responseResult);
        if (jsonParse.get("errcode") != null) {
            String errorCode = jsonParse.get("errcode").toString();
            switch (errorCode) {
                case "0": {
                    return true;
                }
                case "40001": {
                    String appid = AccessToken.getAppidByAccessToken(accessToken_);
                    if (appid.isEmpty()) {
                        Logger.error("AccessToken Handler Error!");
                        return false;
                    }

                    accessToken_ = AccessToken.updateAccessToken(appid, accessToken_);
                    return postRequest(postData_);
                }
                default: {
                    Logger.error("UnHandler Exception!");
                    return false;
                }
            }
        }
        return super.handlerResponse(responseResult);
    }

    private String accessToken_;
}
