package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.MainTransformer;
import org.objectweb.asm.tree.ClassNode;

public class RTGRealisticBiomeBase implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        MainTransformer.logger.info("RTG detected, loading cross-compatibility patch 3! (may be printed multiple times for multiple mods)");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "addBiomes"), 256, 65535, false);
    }
}