package net.tclproject.biomeidextender.asm.transformer.Vanilla;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.ClassNode;

public class VanillaBiomeGenBase implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      AsmUtil.transformInlinedSizeMethod(cn, AsmUtil.findMethod(cn, "<clinit>"), 256, 65536, false);
   }
}
