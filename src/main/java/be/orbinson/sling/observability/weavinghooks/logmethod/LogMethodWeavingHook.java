package be.orbinson.sling.observability.weavinghooks.logmethod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.osgi.framework.hooks.weaving.WovenClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogMethodWeavingHook implements WeavingHook {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String className;
    private final String methodName;
    private final String logLevel;
    private final boolean showGeneratedBytecode;

    public LogMethodWeavingHook(LogMethodWeavingHookConfiguration config) {
        this.className = config.getClassName();
        this.methodName = config.getMethodName();
        this.logLevel = config.getLogLevel();
        this.showGeneratedBytecode = config.isShowGeneratedBytecode();
    }

    @Override
    public void weave(WovenClass wovenClass) {
        if (wovenClass.getClassName().equals(className)) {
            log.debug("Adding dynamic log methods to class  {}", wovenClass.getClassName());
            addLogMethodToClass(wovenClass);
            addDynamicImports(wovenClass);
        }
    }

    private void addLogMethodToClass(WovenClass wovenClass) {
        final ClassReader cr = new ClassReader(wovenClass.getBytes());
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        ClassVisitor logMethodClassVisitor;
        if (showGeneratedBytecode) {
            StringWriter out = new StringWriter();
            final TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(out));
            logMethodClassVisitor = new LogMethodClassVisitor(tcv, className, methodName, logLevel);
            cr.accept(logMethodClassVisitor, 0);
            log.debug("Resulting class bytecode: {}", out);
        } else {
            logMethodClassVisitor = new LogMethodClassVisitor(cw, className, methodName, logLevel);
            cr.accept(logMethodClassVisitor, 0);
        }
        wovenClass.setBytes(cw.toByteArray());
    }

    /**
     * Required to add sl4fj as dynamic import if it would not be used in the bundle
     */
    private void addDynamicImports(WovenClass wovenClass) {
        wovenClass.getDynamicImports().add("org.slf4j");
    }


}
