package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.MainTransformer;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import org.objectweb.asm.tree.ClassNode;

public class RTGChunkProvider implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        MainTransformer.logger.info("RTG detected, loading compatibility patch 2!");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "<init>"), 256, 65535, false);
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "doPopulate"), 256, 65535, false);
    }
}
