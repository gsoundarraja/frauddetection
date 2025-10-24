package spendreport;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import java.util.Random;

public class DetailedTransactionSource implements SourceFunction<DetailedTransaction> {
    private volatile boolean running = true;
    private final Random random = new Random();

    private static final String[] ZIP_CODES = {"01003", "02115", "78712"};
    private static final Long[] ACCOUNT_IDS = {1L, 2L, 3L, 4L, 5L};

    @Override
    public void run(SourceContext<DetailedTransaction> ctx) throws Exception {
        long timestamp = System.currentTimeMillis();

        while (running) {
            Long accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
            String zip = ZIP_CODES[random.nextInt(ZIP_CODES.length)];
            double amount = 0.01 + (1000.0 - 0.01) * random.nextDouble();

            timestamp += 1000;  // Increment by 1 second

            // **FIXED: Pass long timestamp directly, not String.valueOf(timestamp)**
            DetailedTransaction txn = new DetailedTransaction(accountId, timestamp, amount, zip);

            System.out.println("Generated: Account=" + accountId + ", Amount=$" + String.format("%.2f", amount) + ", Zip=" + zip);
            ctx.collect(txn);

            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}