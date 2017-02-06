package pf.esicash.api.RequestBean;

public class RequestData {
    public String channelCode; // 通道编码
    public String merchCode; // 商户号
    public String orderNo; // 商户订单号
    public String typeCode; // 交易类型
    public double tradeTotal; // 交易金额
    public double rate; // 交易费率
    public String goodsName; // 商品名称

    // option
    public String accName; // 收款人姓名
    public String accNo; // 收款人账号
    public String bankName; // 开户行名称
    public String remark; // 摘要
}
