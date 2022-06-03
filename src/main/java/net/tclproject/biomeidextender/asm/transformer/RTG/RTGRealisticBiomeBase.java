package net.tclproject.biomeidextender.asm.transformer.RTG;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.tree.ClassNode;

public class RTGRealisticBiomeBase implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        LogHelper.info("RTG detected, loading compatibility patch 3! (may be printed multiple times for multiple mods)");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "addBiomes"), 256, 65535, false);
    }
}