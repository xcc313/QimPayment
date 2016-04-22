package com.weixin.api;

import com.framework.utils.HttpUtils;
import com.framework.utils.Logger;
import org.apache.commons.collections.functors.ExceptionClosure;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;

public abstract class WeixinAPI {
    public boolean getRequest() throws Exception {
        String apiUri = getAPIUri();
        if (apiUri.isEmpty()) {
            return false;
        }
        Logger.debug("Request Url:\r\n" + apiUri);

        String responseString = HttpUtils.GetRequest(new HttpGet(apiUri), (HttpEntity httpEntity)->
        {
            return EntityUtils.toString(httpEntity, "UTF-8");
        });

        Logger.debug("Response Data:\r\n" + responseString);

        return handlerResponse(responseString);
    }

    public boolean postRequest(String postData) throws Exception {
        String apiUri = getAPIUri();
        if (apiUri.isEmpty()) {
            return false;
        }
        Logger.debug("Request Url:\r\n" + apiUri);

        postData_ = postData;
        Logger.debug("Reqest Data:\r\n" + postData);

        HttpPost httpPost = new HttpPost(apiUri);
        StringEntity postEntity = new StringEntity(postData, "UTF-8");
        httpPost.setEntity(postEntity);

        String responseString = new String();
        try {
            responseString = HttpUtils.PostRequest(httpPost, (HttpEntity httpEntity)->
            {
                return EntityUtils.toString(httpEntity, "UTF-8");
            });
        }
        finally {
            httpPost.abort();
        }

        Logger.debug("Response Data:\r\n" + responseString);

        return handlerResponse(responseString);
    }

    protected abstract String getAPIUri();

    protected boolean handlerResponse(String responseResult) throws Exception {
        return true;
    }

    protected String postData_;
}
