package net.tclproject.biomeidextender.asm.transformer;

import java.util.ListIterator;

import cpw.mods.fml.common.Loader;
import net.tclproject.biomeidextender.asm.AsmTransformException;
import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import net.tclproject.biomeidextender.asm.Name;

public class VanillaAnvilChunkLoader implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.acl_writeChunkToNBT);
      this.transformWriteChunkToNBT(cn, method, obfuscated);
      method = AsmUtil.findMethod(cn, Name.acl_readChunkFromNBT);
      this.transformReadChunkFromNBT(cn, method, obfuscated);
   }

   private void transformWriteChunkToNBT(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      ListIterator iterator = code.iterator();

      AbstractInsnNode insn;
      do {
         if (!iterator.hasNext()) {
            throw new AsmTransformException("can't find Blocks LDC");
         }

         insn = (AbstractInsnNode)iterator.next();
         if (insn.getOpcode() == 18 && ((LdcInsnNode)insn).cst.equals("Blocks")) {
            iterator.remove();
            iterator.next();
            iterator.next();
            iterator.remove();
            iterator.next();
            iterator.remove();
            iterator.add(Name.hooks_writeChunkToNbt.staticInvocation(obfuscated));
         }
      } while(insn.getOpcode() != 25 || insn.getNext().getType() != 9 || !((LdcInsnNode)insn.getNext()).cst.equals("Biomes"));

      iterator.remove();
      iterator.next();
      iterator.remove();
      iterator.next();
      iterator.remove();
      iterator.next();
      iterator.remove();
      iterator.next();
      iterator.remove();
      iterator.next();
      iterator.add(new VarInsnNode(25, 1));
      iterator.add(new VarInsnNode(25, 3));
      iterator.add(Name.hooks_writeChunkBiomeArrayToNbt.staticInvocation(obfuscated));
   }

   private void transformReadChunkFromNBT(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      int part = 0;
      ListIterator iterator = code.iterator();


      if (Loader.isModLoaded("notenoughIDs")) {
         while (iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode) iterator.next();
            if (part == 0) {
               if (insn.getOpcode() == 25 && insn.getNext().getType() == 9 && ((LdcInsnNode) insn.getNext()).cst.equals("Biomes")) {
                  iterator.remove();
                  ++part;
               }
            } else if (part == 1) {
               if (insn.getNext().getOpcode() == 25 && ((VarInsnNode) insn.getNext()).var == 5 && insn.getNext().getNext().getOpcode() == 176) {
                  iterator.remove();
                  iterator.next();
                  iterator.add(new VarInsnNode(25, 5));
                  iterator.add(new VarInsnNode(25, 2));
                  iterator.add(Name.hooks_readChunkBiomeArrayFromNbt.staticInvocation(obfuscated));
                  return;
               }

               iterator.remove();
            }
         }
      } else {
         while (iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode) iterator.next();
            if (part == 0) {
               if (insn.getOpcode() == 18 && ((LdcInsnNode) insn).cst.equals("Blocks")) {
                  iterator.set(Name.hooks_readChunkFromNbt.staticInvocation(obfuscated));
                  ++part;
               }
            } else if (part == 1) {
               iterator.remove();
               if (insn.getOpcode() == 182) {
                  MethodInsnNode node = (MethodInsnNode) insn;
                  if (Name.ebs_setBlockMSBArray.matches(node, obfuscated)) {
                     ++part;
                  }
               }
            } else if (part == 2) {
               if (insn.getType() == 14) {
                  iterator.remove();
                  ++part;
               }
            } else if (part == 3) {
               if (insn.getOpcode() == 25 && insn.getNext().getType() == 9 && ((LdcInsnNode) insn.getNext()).cst.equals("Biomes")) {
                  iterator.remove();
                  ++part;
               }
            } else if (part == 4) {
               if (insn.getNext().getOpcode() == 25 && ((VarInsnNode) insn.getNext()).var == 5 && insn.getNext().getNext().getOpcode() == 176) {
                  iterator.remove();
                  iterator.next();
                  iterator.add(new VarInsnNode(25, 5));
                  iterator.add(new VarInsnNode(25, 2));
                  iterator.add(Name.hooks_readChunkBiomeArrayFromNbt.staticInvocation(obfuscated));
                  return;
               }

               iterator.remove();
            }
         }
      }

      throw new AsmTransformException("no match for part " + part);
   }
}
