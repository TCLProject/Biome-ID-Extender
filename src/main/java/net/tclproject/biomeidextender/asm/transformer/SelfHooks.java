package net.tclproject.biomeidextender.asm.transformer;

import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import net.tclproject.biomeidextender.asm.Name;

public class SelfHooks implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      this.transformGetArray(cn, AsmUtil.findMethod(cn, "getBiomeShortArray"));
      this.transformSetArray(cn, AsmUtil.findMethod(cn, "setBiomeShortArray"));
      this.transformGet(cn, AsmUtil.findMethod(cn, "get"));
      this.transformSetBlockRefCount(cn, AsmUtil.findMethod(cn, "setBlockRefCount"), obfuscated);
      this.transformSetTickRefCount(cn, AsmUtil.findMethod(cn, "setTickRefCount"), obfuscated);
   }

   private void transformGetArray(ClassNode cn, MethodNode method) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new MethodInsnNode(182, "net/minecraft/world/chunk/Chunk", "getBiomeShortArray", "()[S", false));
      code.add(new InsnNode(176));
      method.localVariables = null;
      method.maxStack = 1;
   }

   private void transformSetArray(ClassNode cn, MethodNode method) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 1));
      code.add(new VarInsnNode(25, 0));
      code.add(new MethodInsnNode(182, "net/minecraft/world/chunk/Chunk", "setBiomeShortArray", "([S)V", false));
      code.add(new InsnNode(177));
      method.localVariables = null;
      method.maxStack = 2;
   }

   private void transformGet(ClassNode cn, MethodNode method) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new FieldInsnNode(180, Type.getArgumentTypes(method.desc)[0].getInternalName(), "block16BArray", "[S"));
      code.add(new InsnNode(176));
      method.localVariables = null;
      method.maxStack = 1;
   }

   private void transformSetBlockRefCount(ClassNode cn, MethodNode method, boolean isObf) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new VarInsnNode(21, 1));
      code.add(Name.ebs_blockRefCount.virtualSet(isObf));
      code.add(new InsnNode(177));
      method.localVariables = null;
      method.maxStack = 2;
   }

   private void transformSetTickRefCount(ClassNode cn, MethodNode method, boolean isObf) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new VarInsnNode(21, 1));
      code.add(Name.ebs_tickRefCount.virtualSet(isObf));
      code.add(new InsnNode(177));
      method.localVariables = null;
      method.maxStack = 2;
   }
}
