package mobile.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer {

    private static int MAX_RETRY_COUNT = 3;

    AtomicInteger count = new AtomicInteger(MAX_RETRY_COUNT);

    public boolean isRetryAvailable() {
        return (count.intValue() > 0);
    }

    @Override
    public boolean retry(ITestResult result) {
        boolean retry = false;
        if (isRetryAvailable()) {
            retry = true;
            System.out.println("[RETRYING] : " + result.getMethod());
            count.decrementAndGet();
        }
        return retry;
    }
}