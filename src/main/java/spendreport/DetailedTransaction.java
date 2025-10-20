package spendreport;

public class DetailedTransaction {
    private Long accountId;
    private double amount;
    private String timestamp;
    private String zipCode;

    public DetailedTransaction(Long accountId, double amount, String timestamp, String zipCode) {
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.zipCode = zipCode;
    }

    public Long getAccountId() { return accountId; }
    public String getTimestamp() { return timestamp; }
    public double getAmount() { return amount; }
    public String getZipCode() { return zipCode; }

    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

}
