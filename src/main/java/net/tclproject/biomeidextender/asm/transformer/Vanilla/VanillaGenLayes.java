package net.tclproject.biomeidextender.asm.transformer.Vanilla;

import java.util.ListIterator;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import net.tclproject.biomeidextender.asm.Name;

public class VanillaGenLayes implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.genLayer_geiInts);
      ListIterator<AbstractInsnNode> it = method.instructions.iterator();

      while(it.hasNext()) {
         AbstractInsnNode insn = it.next();
         if (insn.getOpcode() == 17 && ((IntInsnNode)insn).operand == 255 && insn.getNext().getOpcode() == 126) it.set(new LdcInsnNode(65535));
      }

   }
}
