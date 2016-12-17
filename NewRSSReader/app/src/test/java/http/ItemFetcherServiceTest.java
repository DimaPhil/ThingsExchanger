package http;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import odeen.newrssreader.proj.conroller.ItemFetcherService;

/**
 * Created by dmitry on 17.12.16.
 */
@HostReachableRule.HostReachable("google.com")
public class ItemFetcherServiceTest {

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void read() throws IOException {
        InputStream result = new ItemFetcherService().downloadUrl("http://google.com");
        Assert.assertTrue(result.read() >= 0);
    }
}
