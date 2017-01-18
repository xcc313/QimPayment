package pf.paymind.api;

import pf.paymind.api.RequestBean.PayRequestData;

import java.util.Map;

public class WxJsPay extends PayMindAPIWithSign {
    public final static String JSPAY_API = "http://real.izhongyin.com/middlepaytrx/wx/scanCommonCode";

    public WxJsPay(PayRequestData payRequestData) {
        requestData_ = payRequestData;
        requestData_.trxType = "WX_SCANCODE_JSAPI";
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
