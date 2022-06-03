package net.tclproject.biomeidextender.asm.transformer.ATG;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.tree.ClassNode;

public class ATGBiomeManager implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      LogHelper.info("ATG detected, loading compatibility patch 2!");
      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "<clinit>"), 256, 65536, false);
   }
}
