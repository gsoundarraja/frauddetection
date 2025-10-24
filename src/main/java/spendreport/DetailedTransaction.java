package spendreport;

public class DetailedTransaction implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long accountId;
    private long timestamp;  // Changed from String to long
    private double amount;
    private String zipCode;

    public DetailedTransaction(Long accountId, long timestamp, double amount, String zipCode) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.zipCode = zipCode;
    }

    public Long getAccountId() { return accountId; }
    public long getTimestamp() { return timestamp; }  // Changed return type
    public double getAmount() { return amount; }
    public String getZipCode() { return zipCode; }

    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }  // Changed parameter
    public void setAmount(double amount) { this.amount = amount; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}