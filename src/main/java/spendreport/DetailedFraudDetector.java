package spendreport;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * DetailedFraudDetector - Detects fraudulent transactions based on:
 * 1. Small transaction (< $10) followed by large transaction (>= $500)
 * 2. Both transactions must occur in the same zip code
 * 3. Within the same account
 */
public class DetailedFraudDetector extends KeyedProcessFunction<Long, DetailedTransaction, DetailedAlert> {

    private static final long serialVersionUID = 1L;
    private static final double SMALL_AMOUNT = 10.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    // State to remember if we saw a small transaction
    private transient ValueState<Boolean> flagState;

    // State to remember the last small transaction details (including zip code)
    private transient ValueState<DetailedTransaction> lastSmallTransactionState;

    @Override
    public void open(Configuration parameters) {
        // Initialize state for flag indicating if we saw a small transaction
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
                "flag",
                Boolean.class);
        flagState = getRuntimeContext().getState(flagDescriptor);

        // Initialize state for storing the last small transaction details
        ValueStateDescriptor<DetailedTransaction> transactionDescriptor = new ValueStateDescriptor<>(
                "last-small-transaction",
                DetailedTransaction.class);
        lastSmallTransactionState = getRuntimeContext().getState(transactionDescriptor);
    }

    @Override
    public void processElement(
            DetailedTransaction transaction,
            Context context,
            Collector<DetailedAlert> collector) throws Exception {

        // Get the current flag state
        Boolean lastTransactionWasSmall = flagState.value();

        // Check if current transaction is LARGE (>= $500)
        if (transaction.getAmount() >= LARGE_AMOUNT) {
            // Check if we previously had a small transaction for this account
            if (lastTransactionWasSmall != null && lastTransactionWasSmall) {
                // Get the last small transaction details
                DetailedTransaction lastSmall = lastSmallTransactionState.value();

                // Check if zip codes match
                if (lastSmall != null && lastSmall.getZipCode().equals(transaction.getZipCode())) {
                    // FRAUD DETECTED! Same account, small->large, same zip code
                    DetailedAlert alert = new DetailedAlert(
                            transaction.getAccountId(),
                            transaction.getTimestamp(),
                            transaction.getAmount(),
                            transaction.getZipCode()
                    );
                    collector.collect(alert);
                }
            }

            // Clean up - reset state after checking large transaction
            flagState.clear();
            lastSmallTransactionState.clear();
        }

        // Check if current transaction is SMALL (< $10)
        if (transaction.getAmount() < SMALL_AMOUNT) {
            // Mark that we saw a small transaction
            flagState.update(true);
            // Save the small transaction details (including zip code)
            lastSmallTransactionState.update(transaction);
        }
    }
}