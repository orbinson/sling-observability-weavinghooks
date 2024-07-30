package be.orbinson.sling.observability.weavinghooks.logmethod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassVisitor that will add the {@link LogMethodAdapter} when the method name matches with the requested method name
 */
public class LogMethodClassVisitor extends ClassVisitor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String methodName;
    private final String className;
    private final String logLevel;
    private String classDescriptor;

    public LogMethodClassVisitor(ClassVisitor cv, String className, String methodName, String logLevel) {
        super(Opcodes.ASM9, cv);
        this.cv = cv;
        this.className = className;
        this.methodName = methodName;
        this.logLevel = logLevel;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        this.classDescriptor = Type.getObjectType(name).getDescriptor();
    }

    @Override
    public MethodVisitor visitMethod(
            int access,
            String name,
            String desc,
            String signature,
            String[] exceptions) {

        MethodVisitor mv;
        mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null) {
            if (name.equals(methodName)) {
                log.debug("Adding dynamic log method to class {} and method {}", className, methodName);
                mv = new LogMethodAdapter(mv, classDescriptor, methodName, desc, logLevel);
            }
        }
        return mv;
    }
}