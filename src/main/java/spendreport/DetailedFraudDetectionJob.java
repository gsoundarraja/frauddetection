package spendreport;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Detailed Fraud Detection Job
 *
 * This job uses zip code information to detect fraudulent transactions.
 * A fraudulent transaction is defined as a small transaction (< $10)
 * followed by a large transaction (>= $500) for the same account
 * in the same zip code.
 */
public class DetailedFraudDetectionJob {
    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create stream of DetailedTransactions from our custom source
        // The source generates random transactions with account ID, amount, timestamp, and zip code
        DataStream<DetailedTransaction> transactions = env
                .addSource(new DetailedTransactionSource())
                .name("detailed-transactions");

        // Process transactions through fraud detector
        // keyBy groups transactions by account ID so each account is processed separately
        // This allows us to track transaction patterns per account
        DataStream<DetailedAlert> alerts = transactions
                .keyBy(DetailedTransaction::getAccountId)
                .process(new DetailedFraudDetector())
                .name("detailed-fraud-detector");

        // Send alerts to the sink (logs them)
        alerts
                .addSink(new DetailedAlertSink())
                .name("send-detailed-alerts");

        // Execute the job
        env.execute("Detailed Fraud Detection");
    }
}