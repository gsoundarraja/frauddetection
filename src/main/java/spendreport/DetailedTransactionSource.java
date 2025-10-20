package spendreport;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class DetailedTransactionSource implements SourceFunction<DetailedTransaction> {
    private volatile boolean running = true;
    private final Random random = new Random();
    private static final String[] ZIP_CODES = {"01003", "02115", "78712"};
    private static final Long[] ACCOUNT_IDS = {1,2,3,4, 5};

    @Override
    public void run(SourceContext<DetailedTransaction> ctx) throws Exception {
        long timestamp = System.currentTimeMillis();
        while (running) {
            long accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
            String zip = ZIP_CODES[random.nextInt(ZIP_CODES.length)];
            double amount = 0.01 + (1000.0 - 0.01) * random.nextDouble();
            timestamp += 1000;
            DetailedTransaction txn = new DetailedTransaction(accountId, timestamp, amount, zip);
            ctx.collect(txn);
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}