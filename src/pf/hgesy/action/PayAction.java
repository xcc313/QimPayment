package pf.hgesy.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.SessionCache;
import QimCommon.utils.StringUtils;
import QimCommon.utils.Zip;
import pf.database.hgesy.HgesyMerchantInfo;
import pf.database.merchant.SubMerchantUser;
import pf.hgesy.api.RequestBean.DirectPayRequestData;

public class PayAction extends AjaxActionSupport {
    public void jsPay() throws Exception {
        String subMerchantUserId = getParameter("id").toString();
        String body = StringUtils.convertNullableString(getParameter("body").toString());
        double total_fee = Double.parseDouble(getParameter("total_fee").toString()) / 100.0;
        String out_trade_no = StringUtils.convertNullableString(getParameter("out_trade_no"));

        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(subMerchantUserId));
            if (subMerchantUser == null) {
                break;
            }

            HgesyMerchantInfo hgesyMerchantInfo = HgesyMerchantInfo.getMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (hgesyMerchantInfo == null) {
                break;
            }

            DirectPayRequestData directPayRequestData = new DirectPayRequestData();
            directPayRequestData.account = hgesyMerchantInfo.getAccount();
            directPayRequestData.total_fee = String.format("%.2f", total_fee);
            directPayRequestData.subject = body;
            if (!out_trade_no.isEmpty()) {
                directPayRequestData.order_no = out_trade_no;
            }
            directPayRequestData.buildSign(hgesyMerchantInfo.getApiKey());
            String redirectUrl = "http://www.hgesy.com:8080/PayMcc/gateway/direct_pay?";
            redirectUrl += directPayRequestData.buildRequestData();
            String data = String.format("{'id':'%s','body':'%s','url':'%s','data':'%s'}",
                    subMerchantUserId,
                    body,
                    StringUtils.convertNullableString(getParameter("redirect_uri")),
                    StringUtils.convertNullableString(getParameter("data")));
            String zipData = Zip.zip(data);
            getRequest().getSession().setAttribute("data", zipData);
            SessionCache.setSessionData(directPayRequestData.order_no, zipData);
            getResponse().sendRedirect(redirectUrl);
        } while (false);
    }
}