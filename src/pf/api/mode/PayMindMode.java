package pf.api.mode;

import QimCommon.utils.StringUtils;
import pf.ProjectLogger;
import pf.weixin.utils.Signature;

public class PayMindMode extends BaseMode {
    private final static String WeixinScanCode = "PayMindWeixinScanCode";
    private final static String WeixinJsPay = "PayMindWeixinJsPay";
    private final static String AliScanCode = "PayMindAliScanCode";
    private final static String AliJsPay = "PayMindAliJsPay";

    public String scanPay() {
        if (!Signature.checkSignValid(requestBuffer_, requestBuffer_.get("id").toString())) {
            ProjectLogger.error("PayMindMode.scanPay.checkSignValid.Failed!");
            return super.jsPay();
        }

        if (StringUtils.convertNullableString(requestBuffer_.get("method")).toLowerCase().compareTo("alipay.scanpay") == 0) {
            return AliScanCode;
        }

        return WeixinScanCode;
    }

    public String jsPay() {
        if (!Signature.checkSignValid(requestBuffer_, requestBuffer_.get("id").toString())) {
            ProjectLogger.error("PayMindMode.jsPay.checkSignValid.Failed!");
            return super.jsPay();
        }

        if (StringUtils.convertNullableString(requestBuffer_.get("method")).toLowerCase().compareTo("alipay.scanpay") == 0) {
            return AliJsPay;
        }

        return WeixinJsPay;
    }
}
