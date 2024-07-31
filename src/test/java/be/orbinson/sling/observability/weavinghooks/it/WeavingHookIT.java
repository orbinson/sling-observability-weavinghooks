package be.orbinson.sling.observability.weavinghooks.it;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class WeavingHookIT {

    @Test
    public void aaSlingAppIsUp() throws Exception {

        int port = Integer.getInteger("HTTP_PORT", 8080);

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("http://localhost:" + port + "/");
            for (int i = 0; i < 30; i++) {
                try (CloseableHttpResponse response = httpclient.execute(get)) {
                    System.out.println("Status line = " + response.getStatusLine().toString());
                    int statusCode = response.getStatusLine().getStatusCode();
                    if ((statusCode / 100 < 5)) {
                        System.out.println("App is ready");
                        return;
                    }
                    Thread.sleep(1000l);
                }
            }

            fail("App is not yet ready, failing");
        }
    }
}
