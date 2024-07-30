package be.orbinson.sling.observability.weavinghooks.logmethod;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * Method visitor that will add a LoggerFactory log statement call of all parameters when entering a method
 */
public class LogMethodAdapter extends MethodVisitor {

    private final String classDescriptor;
    private final String methodName;
    private final String methodDescriptor;
    private final String logLevel;

    public LogMethodAdapter(MethodVisitor mv, String classDescriptor, String methodName, String methodDescriptor, String logLevel) {
        super(ASM9, mv);
        this.classDescriptor = classDescriptor;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        this.logLevel = logLevel;
    }


    @Override
    public void visitCode() {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitLdcInsn(Type.getType(classDescriptor));
        mv.visitMethodInsn(INVOKESTATIC, "org/slf4j/LoggerFactory", "getLogger", "(Ljava/lang/Class;)Lorg/slf4j/Logger;", false);
        StringBuilder logString = new StringBuilder(methodName);
        Type[] types = Type.getArgumentTypes(methodDescriptor);
        for (int i = 0; i < types.length; i++) {
            logString.append(String.format(", param_%s: <{}>", i + 1));
        }
        mv.visitLdcInsn(logString.toString());
        mv.visitInsn(ICONST_0 + types.length);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        for (int i = 0; i < types.length; i++) {
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0 + i);
            mv.visitVarInsn(ALOAD, i + 1);
            mv.visitInsn(AASTORE);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "org/slf4j/Logger", logLevel.toLowerCase(), "(Ljava/lang/String;[Ljava/lang/Object;)V", true);
    }
}
