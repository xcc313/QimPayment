package pf.jdpay.api.RequestBean;

public class QueryRequestData extends RequestData {
    public QueryRequestData() {
    }

    public boolean checkParameter() {
        try {
            return  !merchant_no.isEmpty()
                    && !order_no.isEmpty();
        }
        catch (Exception exception) {

        }

        return false;
    }
}