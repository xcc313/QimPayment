package pf.database.hgesy;

public class HgesyOrderInfo {
    public static HgesyOrderInfo getOrderInfoByOrderNo(String orderNo) {
        String statement = "pf.database.hgesy.mapping.orderInfo.getOrderInfoByTradeNo";
        return pf.database.swiftpass.Database.Instance().selectOne(statement, orderNo);
    }

    public static boolean insertOrderInfo(HgesyOrderInfo swiftOrderInfo) {
        String statement = "pf.database.hgesy.mapping.orderInfo.insertOrderInfo";
        return pf.database.swiftpass.Database.Instance().insert(statement, swiftOrderInfo) == 1;
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        id_ = id;
    }

    public long getMchId() {
        return mchId_;
    }

    public void setMchId(long mchId) {
        this.mchId_ = mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo_;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo_ = outTradeNo;
    }

    public String getBody() {
        return body_;
    }

    public void setBody(String body) {
        body_ = body;
    }

    public int getTotalFee() {
        return totalFee_;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee_ = totalFee;
    }

    public String getTimeEnd() {
        return timeEnd_;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd_ = timeEnd;
    }

    int id_;
    private long mchId_;
    private String outTradeNo_;
    private String body_;
    private int totalFee_;
    private String timeEnd_;
}
