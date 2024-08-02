package be.orbinson.sling.observability.weavinghooks.util;

import org.slf4j.LoggerFactory;

public class MyClass {

    public boolean callMethod(String input, String secondParameter, String thirdParameter) {
        LoggerFactory.getLogger(MyClass.class).info("{}", new Object[]{input, secondParameter, thirdParameter});
        return true;
    }
}
