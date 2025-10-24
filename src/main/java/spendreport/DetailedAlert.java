package spendreport;

public class DetailedAlert implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long accountId;
    private long timestamp;
    private double amount;
    private String zipCode;

    public DetailedAlert(Long accountId, long timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode;
    }

    public Long getAccountId() { return accountId; }
    public long getTimestamp() { return timestamp; }
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