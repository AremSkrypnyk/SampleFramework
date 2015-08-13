package ipreomobile.data;

import java.util.Arrays;
import java.util.List;

public class FixedIncomeData extends TestDataObject {

    @TestDataField
    private String couponName;

    @TestDataField
    private String couponNameIssuedByIssuer;

    @TestDataField
    private String issuerName;

    @TestDataField
    private String cusip;

    @TestDataField
    private String isin;

    @TestDataField
    private String labels;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponNameIssuedByIssuer() {
        return couponNameIssuedByIssuer;
    }

    public void setCouponNameIssuedByIssuer(String couponNameIssuedByIssuer) {
        this.couponNameIssuedByIssuer = couponNameIssuedByIssuer;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public List<String> getLabels() {
        List<String> labelsList = Arrays.asList(labels.split(";"));
        for (int entryNumber = 0; entryNumber < labelsList.size(); entryNumber++)
            labelsList.set(entryNumber, String.format(labelsList.get(entryNumber), "(" + System.getProperty("test.currency") + "M)"));
        return labelsList;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}
