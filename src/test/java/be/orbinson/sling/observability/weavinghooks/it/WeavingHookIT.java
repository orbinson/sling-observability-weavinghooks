package be.orbinson.sling.observability.weavinghooks.it;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeavingHookIT {

    private static final int SLING_PORT = Integer.getInteger("HTTP_PORT", 8080);

    /**
     * Uses the {@link be.orbinson.sling.observability.weavinghooks.testservlet.TestServlet} that has two weaved methods on startup
     */
    @Test
    public void testServletShouldLogMethodParams() throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("http://localhost:" + SLING_PORT + "/test");
            try (CloseableHttpResponse response = httpclient.execute(get)) {
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                assertEquals("OK", responseBody);
            }

            HttpGet checkLogs = new HttpGet("http://localhost:" + SLING_PORT + "/system/console/slinglog/tailer.txt?name=/logs/test.log");
            checkLogs.addHeader("Authorization", "Basic " + Base64.encodeBase64String("admin:admin".getBytes(StandardCharsets.UTF_8)));
            try (CloseableHttpResponse response = httpclient.execute(checkLogs)) {
                String[] lines = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8).split("\n");
                assertEquals("TestServlet methodWithOneParameter, param_1: <parameterOne>", lines[0].substring(lines[0].indexOf("TestServlet")));
                assertEquals("TestServlet methodWithTwoParameters, param_1: <parameterOne>, param_2: <parameterTwo>", lines[1].substring(lines[1].indexOf("TestServlet")));
            }
        }
    }
}
