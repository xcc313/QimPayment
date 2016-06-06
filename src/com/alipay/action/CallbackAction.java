package com.alipay.action;

import com.framework.action.AjaxActionSupport;
import com.framework.utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CallbackAction extends AjaxActionSupport {
    public final static String TRADEPRECREATE = "Callback!tradePreCreate";

    public String tradePreCreate() throws Exception {
        Logger.warn("tradePreCreate Callback");
        handlerCallback();
        return AjaxActionComplete();
    }

    private boolean handlerCallback() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getRequest().getInputStream(), "utf-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String lineBuffer;
        while ((lineBuffer = bufferedReader.readLine()) != null) {
            stringBuilder.append(lineBuffer);
        }
        bufferedReader.close();
        return false;
    }
}
