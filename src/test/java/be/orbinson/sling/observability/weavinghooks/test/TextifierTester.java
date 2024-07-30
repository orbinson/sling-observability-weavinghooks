package be.orbinson.sling.observability.weavinghooks.test;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

public class TextifierTester {

    /**
     * Use this method to see the bytecode for a specific class
     **/
    @Test
    public void generateBytecode() throws IOException {
        String className = "be.orbinson.osgi.log.method.weavinghook.test.MyClass";

        PrintWriter output = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, new Textifier(), output);

        int parsingOptions = 0;
        new ClassReader(className).accept(traceClassVisitor, parsingOptions);
    }
}
