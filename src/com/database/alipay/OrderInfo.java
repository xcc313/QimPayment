package com.database.alipay;

public class OrderInfo {
    public static OrderInfo getOrderInfo(long id) {
        String statement = "com.database.alipay.mapping.orderInfo.getOrderInfoById";
        return Database.Instance().selectOne(statement, id);
    }

    public static boolean insertOrderInfo(OrderInfo orderInfo) {
        String statement = "com.database.alipay.mapping.orderInfo.insertOrderInfo";
        return Database.Instance().insert(statement, orderInfo) == 1;
    }

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public String getAppid() {
        return appid_;
    }

    public void setAppid(String appid) {
        this.appid_ = appid;
    }

    public long getMchId() {
        return mchId_;
    }

    public void setMchId(long mchId) {
        this.mchId_ = mchId;
    }

    public String getSubject() {
        return subject_;
    }

    public void setSubject(String subject) {
        this.subject_ = subject;
    }

    public String getTradeNo() {
        return tradeNo_;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo_ = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo_;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo_ = outTradeNo;
    }

    public String getFundChannel() {
        return fundChannel_;
    }

    public void setFundChannel(String fundChannel) {
        this.fundChannel_ = fundChannel;
    }

    public double getTotalAmount() {
        return totalAmount_;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount_ = totalAmount;
    }

    public String getGmtPayment() {
        return gmtPayment_;
    }

    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment_ = gmtPayment;
    }

    public long getCreateUser() {
        return createUser_;
    }

    public void setCreateUser(long createUser) {
        this.createUser_ = createUser;
    }

    public String getOpenId() {
        return openId_;
    }

    public void setOpenId(String openId) {
        this.openId_ = openId;
    }

    private long id_;
    private String appid_;
    private long mchId_;
    private String subject_;
    private String tradeNo_;
    private String outTradeNo_;
    private String fundChannel_;
    private double totalAmount_;
    private String gmtPayment_;
    private long createUser_;
    private String openId_;
}