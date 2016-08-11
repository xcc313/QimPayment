package com.alipay.api;

import com.alipay.api.RequestData.QueryData;
import com.alipay.api.RequestData.RequestData;
import com.alipay.utils.Signature;
import com.framework.utils.ClassUtils;
import com.framework.utils.HttpUtils;
import com.framework.utils.JsonUtils;
import com.framework.utils.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.Map;

public abstract class AliPayAPIWithSign extends AliPayAPI {
    public Map<String, Object> getResponseResult() {
        return responseResult_;
    }

    public boolean postRequest(String privateKey, String publicKey) throws Exception {
        if (!requestData_.checkParameter() || privateKey.isEmpty()) {
            Logger.error(this.getClass().getName() + " CheckParameter Failed!");
            return false;
        }

        requestData_.buildRequestData();
        requestData_.buildSign(privateKey);

        String apiUri = getAPIUri();
        if (apiUri.isEmpty()) {
            return false;
        }
        apiUri += "?" + ClassUtils.convertToQuery(new QueryData(requestData_), "utf-8", true);

        HttpPost httpPost = new HttpPost(apiUri);
        StringEntity postEntity = new StringEntity("biz_content=" + URLEncoder.encode(requestData_.biz_content, "utf-8"), "UTF-8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.addHeader("Accept", "text/xml,text/javascript,text/html");
        httpPost.setEntity(postEntity);

        String responseString = new String();
        try {
            responseString = HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse) -> {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            });
        }
        finally {
            httpPost.abort();
        }

        responseResult_ = JsonUtils.toMap(responseString, true);

        boolean ret = true;
        String rootNode = requestData_.method.replace('.', '_') + "_response";
        if (responseResult_.containsKey("sign") && responseResult_.containsKey(rootNode)) {
            if (!Signature.verifySign(getSignSourceData(rootNode, responseString),
                    responseResult_.get("sign").toString(),
                    publicKey))
                ret = false;
        }

        ret = ret && handlerResponse(responseResult_);

        if (!ret) {
            Logger.error("Request Url:\r\n" + apiUri);
            Logger.error("Response Data:\r\n" + responseString);
        }

        return ret;
    }

    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        return true;
    }

    private String getSignSourceData(String rootNode, String responseString) {
        String errorRootNode = "error_response";
        int indexOfRootNode = responseString.indexOf(rootNode);
        int indexOfErrorRoot = responseString.indexOf(errorRootNode);
        return indexOfRootNode > 0?this.parseSignSourceData(responseString, rootNode, indexOfRootNode):(indexOfErrorRoot > 0?this.parseSignSourceData(responseString, errorRootNode, indexOfErrorRoot):null);
    }

    private String parseSignSourceData(String body, String rootNode, int indexOfRootNode) {
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 2;
        int indexOfSign = body.indexOf("\"sign\"");
        if(indexOfSign < 0) {
            return null;
        } else {
            int signDataEndIndex = indexOfSign - 1;
            return body.substring(signDataStartIndex, signDataEndIndex);
        }
    }

    protected RequestData requestData_;
    protected Map<String, Object> responseResult_;
}
