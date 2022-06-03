package net.tclproject.biomeidextender.asm.transformer.BiomesOPlenty;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.tree.ClassNode;

public class BoPBiomeManager implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      LogHelper.info("Biomes O' Plenty detected, loading compatibility patch!");
      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "getNextFreeBiomeId"), 255, 65535, false);
      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "getNextFreeBiomeId"), 256, 65535, false);
   }
}
