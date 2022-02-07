package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class DAIDType implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        MethodNode method = AsmUtil.findMethod(cn, "<clinit>");
        AsmUtil.transformInlinedSizeMethod(cn, method, 254, 65536, false);

    }
}
