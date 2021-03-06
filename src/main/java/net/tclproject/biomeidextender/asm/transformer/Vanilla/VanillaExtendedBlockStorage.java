package net.tclproject.biomeidextender.asm.transformer.Vanilla;

import java.util.ListIterator;

import net.tclproject.biomeidextender.asm.AsmTransformException;
import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import net.tclproject.biomeidextender.asm.Name;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class VanillaExtendedBlockStorage implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      cn.fields.add(new FieldNode(1, "block16BArray", "[S", null, null));
      AsmUtil.makePublic(AsmUtil.findField(cn, Name.ebs_blockRefCount));
      AsmUtil.makePublic(AsmUtil.findField(cn, Name.ebs_tickRefCount));
      MethodNode method = AsmUtil.findMethod(cn, "<init>");
      this.transformConstructor(cn, method, obfuscated);
      method = AsmUtil.findMethod(cn, Name.ebs_getBlock);
      this.transformGetBlock(cn, method, obfuscated);
      method = AsmUtil.findMethod(cn, Name.ebs_setBlock);
      this.transformSetBlock(cn, method, obfuscated);
      method = AsmUtil.findMethod(cn, Name.ebs_getBlockMSBArray);
      this.transformGetBlockMSBArray(cn, method);
      method = AsmUtil.findMethod(cn, Name.ebs_removeInvalidBlocks);
      this.transformRemoveInvalidBlocks(cn, method);
   }

   private void transformConstructor(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      ListIterator<AbstractInsnNode> iterator = code.iterator();
      if (iterator.hasNext()) {
         AbstractInsnNode insn = iterator.next();
         insn = insn.getNext().getNext();
         InsnList toInsert = new InsnList();
         toInsert.add(new VarInsnNode(25, 0));
         toInsert.add(Name.hooks_create16BArray.staticInvocation(obfuscated));
         toInsert.add(new FieldInsnNode(181, cn.name, "block16BArray", "[S"));
         method.instructions.insert(insn, toInsert);
      }

      method.maxStack = Math.max(method.maxStack, 2);
   }

   private void transformGetBlock(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new VarInsnNode(21, 1));
      code.add(new VarInsnNode(21, 2));
      code.add(new VarInsnNode(21, 3));
      code.add(Name.hooks_getBlockById.staticInvocation(obfuscated));
      code.add(new InsnNode(176));
      method.localVariables = null;
      method.maxStack = 4;
   }

   private void transformSetBlock(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      int part = 0;
      ListIterator<AbstractInsnNode> iterator = code.iterator();

      while(iterator.hasNext()) {
         AbstractInsnNode insn = iterator.next();
         if (part == 0) {
            iterator.remove();
            if (insn.getOpcode() == 184) {
               ++part;
               iterator.add(new VarInsnNode(25, 0));
               iterator.add(new VarInsnNode(21, 1));
               iterator.add(new VarInsnNode(21, 2));
               iterator.add(new VarInsnNode(21, 3));
               iterator.add(Name.ebs_getBlock.virtualInvocation(obfuscated));
            }
         }
         else if (part == 1) {
            if (insn.getOpcode() == 184) ++part;
         }
         else iterator.remove();
      }

      if (part != 2) throw new AsmTransformException("no match for part " + part);
      else {
         code.add(new VarInsnNode(54, 5));
         code.add(new VarInsnNode(25, 0));
         code.add(new VarInsnNode(21, 1));
         code.add(new VarInsnNode(21, 2));
         code.add(new VarInsnNode(21, 3));
         code.add(new VarInsnNode(21, 5));
         code.add(Name.hooks_setBlockId.staticInvocation(obfuscated));
         code.add(new InsnNode(177));
         method.localVariables = null;
         --method.maxLocals;
         method.maxStack = Math.max(method.maxStack, 5);
      }
   }

   private void transformGetBlockMSBArray(ClassNode cn, MethodNode method) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new InsnNode(1));
      code.add(new InsnNode(176));
      method.localVariables = null;
      method.maxStack = 1;
   }

   private void transformRemoveInvalidBlocks(ClassNode cn, MethodNode method) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new MethodInsnNode(184, "net/tclproject/biomeidextender/BiomeUtils", "removeInvalidBlocksHook", "(L" + cn.name + ";)V", false));
      code.add(new InsnNode(177));
      method.localVariables = null;
      method.maxStack = 1;
   }
}
