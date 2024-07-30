package be.orbinson.sling.observability.weavinghooks.test;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

public class ASMifierTester {

    /**
     * Use this method to generate ASM for a specific method
     **/
    @Test
    public void generateASM() throws IOException {
        String className = "be.orbinson.sling.observability.weavinghooks.test.MyClass";

        PrintWriter output = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, new ASMifier(), output);

        int parsingOptions = 0;
        new ClassReader(className).accept(traceClassVisitor, parsingOptions);
    }
}
