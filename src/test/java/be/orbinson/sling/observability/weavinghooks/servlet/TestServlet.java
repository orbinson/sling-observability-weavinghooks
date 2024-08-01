package be.orbinson.sling.observability.weavinghooks.servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import javax.servlet.*;
import java.io.IOException;

/**
 * Servlet used in Integration test to validate that logs are appended
 */
@HttpWhiteboardServletPattern("/test")
@HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=org.osgi.service.http)")
@Component(service = Servlet.class)
public class TestServlet implements Servlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        methodWithOneParameter("parameterOne");
        methodWithTwoParameters("parameterOne","parameterTwo");
    }

    private void methodWithOneParameter(String parameterOne) {
        // do nothing
    }

    private void methodWithTwoParameters(String parameterOne, String parameterTwo) {
        // do nothing
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}
