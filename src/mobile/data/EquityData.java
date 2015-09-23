package ipreomobile.data;

import java.util.Arrays;
import java.util.List;

public class EquityData extends TestDataObject {

    @TestDataField
    private String securityName;

    @TestDataField
    private String tickerName;

    @TestDataField
    private String securityMarketName;

    @TestDataField
    private String labels;

    @TestDataField
    private String issuerName;

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    public String getSecurityMarketName() {
        return securityMarketName;
    }

    public void setSecurityMarketName(String securityMarketName) {
        this.securityMarketName = securityMarketName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
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
