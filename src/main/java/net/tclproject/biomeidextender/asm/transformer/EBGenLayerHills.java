package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.MainTransformer;
import org.objectweb.asm.tree.ClassNode;

public class EBGenLayerHills implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      // this compatibility patch does not do everything needed. The game will still crash
//      MainTransformer.logger.info("Enhanced Biomes detected, loading compatibility patch!");
//      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "getInts"), 255, 65535, false);
//      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "getInts"), 256, 65535, false);

   }
}
