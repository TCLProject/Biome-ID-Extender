package net.tclproject.biomeidextender.asm.transformer.AntiIDConflict;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.tree.ClassNode;

public class ConflictingBiomes implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        LogHelper.info("Anti ID Conflict detected, loading compatibility patch!");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "<clinit>"), 256, 65536, false);
    }
}
