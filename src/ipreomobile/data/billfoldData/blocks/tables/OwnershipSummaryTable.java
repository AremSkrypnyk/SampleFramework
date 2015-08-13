package ipreomobile.data.billfoldData.blocks.tables;

public class OwnershipSummaryTable {

    private String numberOfHoldings;
    private String portfolioValue;
    private String newPositions;
    private String increasedPositions;
    private String decreasedPositions;
    private String unchangedPositions;

    public String getUnchangedPositions() {
        return unchangedPositions;
    }

    public void setUnchangedPositions(String unchangedPositions) {
        this.unchangedPositions = unchangedPositions;
    }

    public String getNumberOfHoldings() {
        return numberOfHoldings;
    }

    public void setNumberOfHoldings(String numberOfHoldings) {
        this.numberOfHoldings = numberOfHoldings;
    }

    public String getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(String portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public String getNewPositions() {
        return newPositions;
    }

    public void setNewPositions(String newPositions) {
        this.newPositions = newPositions;
    }

    public String getIncreasedPositions() {
        return increasedPositions;
    }

    public void setIncreasedPositions(String increasedPositions) {
        this.increasedPositions = increasedPositions;
    }

    public String getDecreasedPositions() {
        return decreasedPositions;
    }

    public void setDecreasedPositions(String decreasedPositions) {
        this.decreasedPositions = decreasedPositions;
    }

}
