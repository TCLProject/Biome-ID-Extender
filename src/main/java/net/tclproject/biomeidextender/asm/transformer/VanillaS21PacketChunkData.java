package net.tclproject.biomeidextender.asm.transformer;

import java.util.ListIterator;

import cpw.mods.fml.common.Loader;
import net.tclproject.biomeidextender.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class VanillaS21PacketChunkData implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {

      MethodNode method = AsmUtil.findMethod(cn, "<clinit>");

      if (!Loader.isModLoaded("notenoughIDs")) {
         AsmUtil.transformInlinedSizeMethod(cn, method, 196864, 229888, false);
         method = AsmUtil.findMethod(cn, Name.s21_undefined1);
         AsmUtil.transformInlinedSizeMethod(cn, method, 196864, 229888, false);
         method = AsmUtil.findMethod(cn, Name.packet_readPacketData);
         AsmUtil.transformInlinedSizeMethod(cn, method, 12288, 14336, false);
      } else {
         MainTransformer.logger.info("Detected NotEnoughIDs, performing modified transform 1 on S21PacketChunkData..");
      }

      method = AsmUtil.findMethod(cn, Name.s21_undefined2);
      this.transformCreateData(cn, method, obfuscated);
   }

   private void transformCreateData(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      int part = 0;
      ListIterator iterator = code.iterator();

      if (Loader.isModLoaded("notenoughIDs")) {
         MainTransformer.logger.info("Detected NotEnoughIDs, performing modified transform 2 on S21PacketChunkData..");
         while (iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode) iterator.next();
            method.maxStack = 6;
            if (part == 0) {
               if (insn.getNext().getOpcode() == 182 && ((MethodInsnNode) insn.getNext()).name.equals(Name.chunk_getBiomeArray.getName(obfuscated))) {
                  iterator.remove();
                  iterator.next();
                  iterator.remove();
                  iterator.next();
                  iterator.remove();
                  iterator.add(new VarInsnNode(25, 0));
                  iterator.add(Name.chunk_getBiomeShortArray.virtualInvocation(obfuscated));
                  iterator.add(Name.hooks_fromShortToByteArray.staticInvocation(obfuscated));
                  iterator.add(new VarInsnNode(58, 10));
                  iterator.next();
                  iterator.next();
                  ++part;
               }
            } else if (part == 1) {
               if (insn.getOpcode() == 184 && ((MethodInsnNode) insn).name.equals("arraycopy")) {
                  iterator.remove();
                  iterator.add(new VarInsnNode(25, 10));
                  iterator.add(new InsnNode(3));
                  iterator.add(new VarInsnNode(25, 7));
                  iterator.add(new VarInsnNode(21, 3));
                  iterator.add(new IntInsnNode(17, 512));
                  iterator.add(new MethodInsnNode(184, "java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", false));
                  return;
               }

               iterator.remove();
            }
         }
      } else {
         while (iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode) iterator.next();
            method.maxStack = 6;
            if (part == 0) {
               if (insn.getOpcode() == 182) {
                  MethodInsnNode node = (MethodInsnNode) insn;
                  if (Name.ebs_getBlockLSBArray.matches(node, obfuscated)) {
                     iterator.set(Name.hooks_getBlockData.staticInvocation(obfuscated));

                     ++part;
                  }
               }
            } else if (part == 1) {
               if (insn.getNext().getOpcode() == 182 && ((MethodInsnNode) insn.getNext()).name.equals(Name.chunk_getBiomeArray.getName(obfuscated))) {
                  iterator.remove();
                  iterator.next();
                  iterator.remove();
                  iterator.next();
                  iterator.remove();
                  iterator.add(new VarInsnNode(25, 0));
                  iterator.add(Name.chunk_getBiomeShortArray.virtualInvocation(obfuscated));
                  iterator.add(Name.hooks_fromShortToByteArray.staticInvocation(obfuscated));
                  iterator.add(new VarInsnNode(58, 10));
                  iterator.next();
                  iterator.next();
                  ++part;
               }
            } else if (part == 2) {
               if (insn.getOpcode() == 184 && ((MethodInsnNode) insn).name.equals("arraycopy")) {
                  iterator.remove();
                  iterator.add(new VarInsnNode(25, 10));
                  iterator.add(new InsnNode(3));
                  iterator.add(new VarInsnNode(25, 7));
                  iterator.add(new VarInsnNode(21, 3));
                  iterator.add(new IntInsnNode(17, 512));
                  iterator.add(new MethodInsnNode(184, "java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", false));
                  return;
               }

               iterator.remove();
            }
         }
      }

      throw new AsmTransformException("can't find getBlockLSBArray INVOKEVIRTUAL");
   }
}
