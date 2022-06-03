package net.tclproject.biomeidextender.asm.transformer.Vanilla;

import cpw.mods.fml.common.Loader;
import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class VanillaS26PacketMapChunkBulk implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.packet_readPacketData);
      if (!Loader.isModLoaded("notenoughIDs")) AsmUtil.transformInlinedSizeMethod(cn, method, 8192, 12288, false);
      AsmUtil.transformInlinedSizeMethod(cn, method, 256, 512, false);
   }
}
