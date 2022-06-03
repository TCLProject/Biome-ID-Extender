package net.tclproject.biomeidextender.asm.transformer.DragonAPI;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.tree.ClassNode;

public class DAIDType implements IClassNodeTransformer {
    public void transform(ClassNode cn, boolean obfuscated) {
        LogHelper.info("DragonAPI detected, loading compatibility patch!");
        AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "<clinit>"), 254, 65536, false);
    }
}
