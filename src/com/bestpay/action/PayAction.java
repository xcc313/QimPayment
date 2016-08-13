package com.bestpay.action;

import com.bestpay.api.BarcodePay;
import com.bestpay.api.RequestData.BarcodePayRequestData;
import com.database.bestpay.BtMerchantInfo;
import com.database.merchant.SubMerchantInfo;
import com.database.merchant.SubMerchantUser;
import com.framework.action.AjaxActionSupport;

public class PayAction extends AjaxActionSupport {
    public String barcodePay() throws Exception {
        do {
            SubMerchantUser subMerchantUser = SubMerchantUser.getSubMerchantUserById(Long.parseLong(getParameter("id").toString()));
            if (subMerchantUser == null) {
                break;
            }

            SubMerchantInfo subMerchantInfo = SubMerchantInfo.getSubMerchantInfoById(subMerchantUser.getSubMerchantId());
            if (subMerchantInfo == null) {
                break;
            }

            BtMerchantInfo merchantInfo = BtMerchantInfo.getMerchantInfoById(subMerchantInfo.getMerchantId());
            if (merchantInfo == null) {
                break;
            }

            BarcodePayRequestData barcodePayRequestData = new BarcodePayRequestData();
            barcodePayRequestData.subMerchantId = barcodePayRequestData.merchantId = merchantInfo.getMchId();
            barcodePayRequestData.barcode = getParameter("barcode").toString();
            if (getParameter("orderNo") != null) {
                barcodePayRequestData.orderReqNo = barcodePayRequestData.orderNo = getParameter("orderNo").toString();
            }

            barcodePayRequestData.orderAmt = barcodePayRequestData.productAmt = Integer.parseInt(getParameter("productAmt").toString());
            if (getParameter("attachAmt") != null) {
                barcodePayRequestData.attachAmt = Integer.parseInt(getParameter("attachAmt").toString());
                barcodePayRequestData.orderAmt += barcodePayRequestData.attachAmt;
            }

            barcodePayRequestData.storeId = "000000";

            BarcodePay barcodePay = new BarcodePay(barcodePayRequestData);
            return AjaxActionComplete(barcodePay.postRequest(merchantInfo.getApiKey()));
        } while (false);
        return AjaxActionComplete();
    }
}
