package pf.paymind.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.SessionCache;
import QimCommon.utils.StringUtils;
import QimCommon.utils.Zip;
import pf.database.merchant.SubMerchantUser;
import pf.database.paymind.PmMerchantInfo;
import pf.paymind.api.AliJsPay;
import pf.paymind.api.AliScanCode;
import pf.paymind.api.WxJsPay;
import pf.paymind.api.RequestBean.PayRequestData;
import pf.paymind.api.WxScanCode;

import java.util.HashMap;
import java.util.Map;

public class PayAction extends AjaxActionSupport {
    public String wxScanPay() throws Exception {
        String subMerchantUserId = getParameter("id").toString();
        String body = StringUtils.convertNullableString(getParameter("body").toString());
        double total_fee = Double.parseDouble(getParameter("total_fee").toString());
        String out_trade_no = StringUtils.convertNullableString(getParameter("out_trade_no"));

        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(subMerchantUserId));
            if (subMerchantUser == null) {
                break;
            }

            PmMerchantInfo pmMerchantInfo = PmMerchantInfo.getMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (pmMerchantInfo == null) {
                break;
            }

            PayRequestData payRequestData = new PayRequestData();
            payRequestData.amount = total_fee;
            payRequestData.goodsName = body;
            payRequestData.merchantNo = pmMerchantInfo.getMchId();
            String requestUrl = getRequest().getRequestURL().toString();
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "paymind/"
                    + CallbackAction.JSPAYCALLBACK;
            payRequestData.serverCallbackUrl = requestUrl;
            if (!out_trade_no.isEmpty()) {
                payRequestData.orderNum = out_trade_no;
            }
            WxScanCode scanCode = new WxScanCode(payRequestData);
            if (scanCode.postRequest(pmMerchantInfo.getApiKey())) {
                String data = String.format("{'id':'%s','body':'%s','url':'%s','data':'%s'}",
                        subMerchantUserId,
                        body,
                        StringUtils.convertNullableString(getParameter("redirect_uri")),
                        StringUtils.convertNullableString(getParameter("data")));
                String zipData = Zip.zip(data);
                getRequest().getSession().setAttribute("data", zipData);
                SessionCache.setSessionData(payRequestData.orderNum, zipData);
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("code_url", scanCode.getPayUrl());
                return AjaxActionComplete(resultMap);
            }
        } while (false);

        return AjaxActionComplete(false);
    }

    public String aliScanPay() throws Exception {
        String subMerchantUserId = getParameter("id").toString();
        String body = StringUtils.convertNullableString(getParameter("body").toString());
        double total_fee = Double.parseDouble(getParameter("total_fee").toString());
        String out_trade_no = StringUtils.convertNullableString(getParameter("out_trade_no"));

        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(subMerchantUserId));
            if (subMerchantUser == null) {
                break;
            }

            PmMerchantInfo pmMerchantInfo = PmMerchantInfo.getMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (pmMerchantInfo == null) {
                break;
            }

            PayRequestData payRequestData = new PayRequestData();
            payRequestData.amount = total_fee;
            payRequestData.goodsName = body;
            payRequestData.merchantNo = pmMerchantInfo.getMchId();
            String requestUrl = getRequest().getRequestURL().toString();
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "paymind/"
                    + CallbackAction.JSPAYCALLBACK;
            payRequestData.serverCallbackUrl = requestUrl;
            if (!out_trade_no.isEmpty()) {
                payRequestData.orderNum = out_trade_no;
            }
            AliScanCode scanCode = new AliScanCode(payRequestData);
            if (scanCode.postRequest(pmMerchantInfo.getApiKey())) {
                String data = String.format("{'id':'%s','body':'%s','url':'%s','data':'%s'}",
                        subMerchantUserId,
                        body,
                        StringUtils.convertNullableString(getParameter("redirect_uri")),
                        StringUtils.convertNullableString(getParameter("data")));
                String zipData = Zip.zip(data);
                getRequest().getSession().setAttribute("data", zipData);
                SessionCache.setSessionData(payRequestData.orderNum, zipData);
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("code_url", scanCode.getPayUrl());
                return AjaxActionComplete(resultMap);
            }
        } while (false);

        return AjaxActionComplete(false);
    }

    public void wxJsPay() throws Exception {
        String subMerchantUserId = getParameter("id").toString();
        String body = StringUtils.convertNullableString(getParameter("body").toString());
        double total_fee = Double.parseDouble(getParameter("total_fee").toString());
        String out_trade_no = StringUtils.convertNullableString(getParameter("out_trade_no"));

        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(subMerchantUserId));
            if (subMerchantUser == null) {
                break;
            }

            PmMerchantInfo pmMerchantInfo = PmMerchantInfo.getMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (pmMerchantInfo == null) {
                break;
            }

            PayRequestData payRequestData = new PayRequestData();
            payRequestData.amount = total_fee;
            payRequestData.goodsName = body;
            payRequestData.merchantNo = pmMerchantInfo.getMchId();
            String requestUrl = getRequest().getRequestURL().toString();
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "paymind/"
                    + CallbackAction.JSPAYCALLBACK;
            payRequestData.serverCallbackUrl = requestUrl;
            if (!out_trade_no.isEmpty()) {
                payRequestData.orderNum = out_trade_no;
            }
            WxJsPay jsPay = new WxJsPay(payRequestData);
            if (jsPay.postRequest(pmMerchantInfo.getApiKey())) {
                String data = String.format("{'id':'%s','body':'%s','url':'%s','data':'%s'}",
                        subMerchantUserId,
                        body,
                        StringUtils.convertNullableString(getParameter("redirect_uri")),
                        StringUtils.convertNullableString(getParameter("data")));
                String zipData = Zip.zip(data);
                getRequest().getSession().setAttribute("data", zipData);
                SessionCache.setSessionData(payRequestData.orderNum, zipData);
                getResponse().sendRedirect(jsPay.getPayUrl());
            }
        } while (false);
    }

    public void aliJsPay() throws Exception {
        String subMerchantUserId = getParameter("id").toString();
        String body = StringUtils.convertNullableString(getParameter("body").toString());
        double total_fee = Double.parseDouble(getParameter("total_fee").toString());
        String out_trade_no = StringUtils.convertNullableString(getParameter("out_trade_no"));

        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(subMerchantUserId));
            if (subMerchantUser == null) {
                break;
            }

            PmMerchantInfo pmMerchantInfo = PmMerchantInfo.getMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (pmMerchantInfo == null) {
                break;
            }

            PayRequestData payRequestData = new PayRequestData();
            payRequestData.amount = total_fee;
            payRequestData.goodsName = body;
            payRequestData.merchantNo = pmMerchantInfo.getMchId();
            String requestUrl = getRequest().getRequestURL().toString();
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "paymind/"
                    + CallbackAction.JSPAYCALLBACK;
            payRequestData.serverCallbackUrl = requestUrl;
            if (!out_trade_no.isEmpty()) {
                payRequestData.orderNum = out_trade_no;
            }
            AliJsPay jsPay = new AliJsPay(payRequestData);
            if (jsPay.postRequest(pmMerchantInfo.getApiKey())) {
                String data = String.format("{'id':'%s','body':'%s','url':'%s','data':'%s'}",
                        subMerchantUserId,
                        body,
                        StringUtils.convertNullableString(getParameter("redirect_uri")),
                        StringUtils.convertNullableString(getParameter("data")));
                String zipData = Zip.zip(data);
                getRequest().getSession().setAttribute("data", zipData);
                SessionCache.setSessionData(payRequestData.orderNum, zipData);
                getResponse().sendRedirect(jsPay.getPayUrl());
            }
        } while (false);
    }
}
