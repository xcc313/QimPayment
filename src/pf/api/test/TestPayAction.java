package pf.api.test;

import pf.api.test.RequestData.JsPayRequestData;
import pf.api.test.RequestData.MicroPayRequestData;
import pf.api.test.RequestData.ScanPayRequestData;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.HttpUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import pf.weixin.utils.Signature;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class TestPayAction extends AjaxActionSupport {
    public void microPay() throws Exception {
        MicroPayRequestData microPayRequestData = new MicroPayRequestData();
        microPayRequestData.id = getParameter("id").toString();
        microPayRequestData.body = getParameter("body").toString();
        microPayRequestData.total_fee = Integer.parseInt(getParameter("total_fee").toString());
        microPayRequestData.auth_code = getParameter("auth_code").toString();
        microPayRequestData.mode = getParameter("mode").toString();
        microPayRequestData.sign = Signature.generateSign(microPayRequestData, microPayRequestData.id);
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(microPayRequestData);
        HttpPost httpPost = new HttpPost(getRequest().getRequestURL().substring(0, getRequest().getRequestURL().lastIndexOf("/") + 1) + "MicroPay");
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        String responseString = new String();
        try {
            responseString = HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse)->
            {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            });
        }
        finally {
            httpPost.abort();
        }
    }

    public String scanPay() throws Exception {
        ScanPayRequestData scanPayRequestData = new ScanPayRequestData();
        scanPayRequestData.mode = "Hgesy";
        scanPayRequestData.method = "alipay.scanpay";
        scanPayRequestData.id = getParameter("id").toString();
        scanPayRequestData.body = "123";
        scanPayRequestData.total_fee = Integer.parseInt(getParameter("total_fee").toString());
        scanPayRequestData.sign = Signature.generateSign(scanPayRequestData, scanPayRequestData.id);
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(scanPayRequestData);
        HttpPost httpPost = new HttpPost("http://localhost:8080/api/ScanPay");
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        String responseString = new String();
        try {
            responseString = HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse)->
            {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            });
        }
            finally {
            httpPost.abort();
        }

        responseString = responseString.substring(1);
        responseString = responseString.substring(0, responseString.length() - 1);
        responseString = responseString.replace("\\", "");
        JSONObject jsonParse = JSONObject.fromObject(responseString);
        Map<String, String> map = new HashMap<>();
        map.put("code_url", jsonParse.get("code_url").toString());
        return AjaxActionComplete(map);
    }

    public void jsPay() throws Exception {
        JsPayRequestData jsPayRequestData = new JsPayRequestData();
        jsPayRequestData.mode = "SwiftPass";
        jsPayRequestData.id = getParameter("id").toString();
        jsPayRequestData.body = "123";
        jsPayRequestData.total_fee = getParameter("total_fee").toString();
        jsPayRequestData.method = "weixin.jspay";
        jsPayRequestData.sign = Signature.generateSign(jsPayRequestData, jsPayRequestData.id);
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(jsPayRequestData);
        HttpPost httpPost = new HttpPost("http://www.qimpay.com/qlpay/api/JsPay");
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        try {
            HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse)->
            {
                Header[] headers = httpResponse.getHeaders("Location");
                if (headers.length != 0)
                    getResponse().sendRedirect(headers[0].getValue());
                return null;
            });
        }
        finally {
            httpPost.abort();
        }
    }

    public String brandWCPay() {
        return AjaxActionComplete();
    }
}