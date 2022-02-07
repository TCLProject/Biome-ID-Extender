package net.tclproject.biomeidextender.asm.transformer;

import java.util.ListIterator;

import net.tclproject.biomeidextender.asm.AsmTransformException;
import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import net.tclproject.biomeidextender.asm.Name;

public class VanillaNetHandlerPlayClient implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.nhpc_handleMultiBlockChange);
      InsnList code = method.instructions;
      int part = 0;
      ListIterator iterator = code.iterator();

      while(iterator.hasNext()) {
         AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
         if (part == 0) {
            if (insn.getOpcode() == 25 && ((VarInsnNode)insn).var == 4) {
               ++part;
            }
         } else if (part == 1) {
            if (insn.getOpcode() == 25 && ((VarInsnNode)insn).var == 4) {
               iterator.remove();
               insn = (AbstractInsnNode)iterator.next();
               iterator.set(new InsnNode(3));
               ++part;
            }
         } else if (part == 2) {
            if (insn.getOpcode() == 21 && ((VarInsnNode)insn).var == 7) {
               iterator.set(new VarInsnNode(25, 4));
               iterator.add(new MethodInsnNode(182, "java/io/DataInputStream", "readShort", "()S", false));
               ++part;
            }
         } else if (part == 3) {
            if (insn.getOpcode() == 54) {
               ++part;
            } else {
               iterator.remove();
            }
         } else if (part == 4) {
            if (insn.getOpcode() == 21 && ((VarInsnNode)insn).var == 7) {
               iterator.set(new VarInsnNode(25, 4));
               iterator.add(new MethodInsnNode(182, "java/io/DataInputStream", "readByte", "()B", false));
               iterator.add(new IntInsnNode(16, 15));
               iterator.add(new InsnNode(126));
               ++part;
            }
         } else if (part == 5) {
            if (insn.getOpcode() == 54) {
               return;
            }

            iterator.remove();
         }
      }

      throw new AsmTransformException("no match for part " + part);
   }
}
