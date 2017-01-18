package pf.paymind.api;

import pf.paymind.api.RequestBean.PayRequestData;

import java.util.Map;

public class AliScanCode extends PayMindAPIWithSign {
    public final static String SCANCODE_API = "http://api.izhongyin.com/middlepaytrx/alipay/scanCode";

    public AliScanCode(PayRequestData payRequestData) {
        requestData_ = payRequestData;
        requestData_.trxType = "Alipay_SCANCODE";
    }

    public String getPayUrl() {
        return payUrl_;
    }

    @Override
    protected String getAPIUri() {
        return SCANCODE_API;
    }

    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        payUrl_ = responseResult.get("qrCode").toString();
        return true;
    }

    private String payUrl_;
}
