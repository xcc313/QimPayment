package pf.paymind.api;

import pf.paymind.api.RequestBean.PayRequestData;

import java.util.Map;

public class AliJsPay extends PayMindAPIWithSign {
    public final static String JSPAY_API = "http://api.izhongyin.com/middlepaytrx/alipay/scanCommonCode";

    public AliJsPay(PayRequestData payRequestData) {
        requestData_ = payRequestData;
        requestData_.trxType = "Alipay_SCANCODE_JSAPI";
    }

    public String getPayUrl() {
        return payUrl_;
    }

    @Override
    protected String getAPIUri() {
        return JSPAY_API;
    }

    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        payUrl_ = responseResult.get("qrCode").toString();
        return true;
    }

    private String payUrl_;
}
