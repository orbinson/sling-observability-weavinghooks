package be.orbinson.sling.observability.weavinghooks.testservlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used in {@link be.orbinson.sling.observability.weavinghooks.it.WeavingHookIT} Integration test to validate that logs are appended
 */
@HttpWhiteboardServletPattern("/test")
@HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=org.osgi.service.http)")
@Component(service = Servlet.class)
public class TestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        methodWithOneParameter("parameterOne");
        methodWithTwoParameters("parameterOne", "parameterTwo");
        resp.getWriter().print("OK");
        resp.getWriter().flush();
    }

    private void methodWithOneParameter(String parameterOne) {
        // do nothing
    }

    private void methodWithTwoParameters(String parameterOne, String parameterTwo) {
        // do nothing
    }

}
