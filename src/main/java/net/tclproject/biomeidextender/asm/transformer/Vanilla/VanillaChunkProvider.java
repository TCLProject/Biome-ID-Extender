package net.tclproject.biomeidextender.asm.transformer.Vanilla;

import java.util.ListIterator;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import net.tclproject.biomeidextender.asm.Name;

public class VanillaChunkProvider implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.anyChunkProvider_provideChunk);
      ListIterator<AbstractInsnNode> it = method.instructions.iterator();

      while(it.hasNext()) {
         AbstractInsnNode insn = it.next();

         if (insn.getOpcode() == 182 && ((MethodInsnNode)insn).name.equals(Name.chunk_getBiomeArray.getName(obfuscated))) it.set(Name.chunk_getBiomeShortArray.virtualInvocation(obfuscated));
         else if (insn.getOpcode() == 84) it.set(new InsnNode(86));
         else if (insn.getOpcode() == 145) it.set(new InsnNode(147));
      }
   }
}
