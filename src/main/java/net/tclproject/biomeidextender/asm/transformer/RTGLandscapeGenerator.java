package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.MainTransformer;
import org.objectweb.asm.tree.ClassNode;

public class RTGLandscapeGenerator implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        MainTransformer.logger.info("RTG detected, loading compatibility patch!");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "getNewerNoise"), 256, 65535, false);
    }
}
