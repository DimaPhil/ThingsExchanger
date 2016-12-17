package http;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

class HostReachableRule implements TestRule {
    private static final int TIMEOUT = 1_000;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    @interface HostReachable {
        String value();
    }

    private static boolean checkHost(String host) {
        return nativePing(host) || nativePing6(host);
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        HostReachable hostReachable = description.getAnnotation(HostReachable.class);

        if (hostReachable == null) {
            return statement;
        } else if (!checkHost(hostReachable.value())) {
            return new SkipStatement(hostReachable.value());
        }

        return statement;
    }

    private static class SkipStatement extends Statement {
        private final String host;

        SkipStatement(String host) {
            this.host = host;
        }

        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue("Skipped, because following host is not available at the moment: " + host, false);
        }
    }

    private static boolean nativePing(String host) {
        return nativePingImpl("ping", host);
    }

    private static boolean nativePing6(String host) {
        return nativePingImpl("ping6", host);
    }

    private static boolean waitFor(Process process, long timeout, TimeUnit unit)
            throws InterruptedException {
        long startTime = System.nanoTime();
        long rem = unit.toNanos(timeout);

        do {
            try {
                process.exitValue();
                return true;
            } catch (IllegalThreadStateException ex) {
                if (rem > 0) {
                    Thread.sleep(
                            Math.min(TimeUnit.NANOSECONDS.toMillis(rem) + 1, 100));
                }
            }
            rem = unit.toNanos(timeout) - (System.nanoTime() - startTime);
        } while (rem > 0);
        return false;
    }

    private static boolean nativePingImpl(String cmd, String host) {
        try {
            Process pingProcess = new ProcessBuilder(cmd, "-c", "1", host).start();
            return waitFor(pingProcess, TIMEOUT, TimeUnit.MILLISECONDS) && pingProcess.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}