package spendreport;

public class DetailedAlert implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long accountId;
    private String timestamp;
    private double amount;
    private String zipCode;

    public DetailedAlert(Long accountId, String timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode;
    }

    public Long getAccountId() { return accountId; }
    public String getTimestamp() { return timestamp; }
    public double getAmount() { return amount; }
    public String getZipCode() { return zipCode; }

    @Override
    public String toString() {
        return "DetailedAlert{" +
                "accountId=" + accountId +
                ", timestamp='" + timestamp + '\'' +
                ", amount=" + amount +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}