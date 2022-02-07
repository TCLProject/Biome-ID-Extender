package net.tclproject.biomeidextender.asm.transformer;

import java.util.ListIterator;

import cpw.mods.fml.common.Loader;
import net.tclproject.biomeidextender.asm.AsmTransformException;
import net.tclproject.biomeidextender.asm.AsmUtil;
import net.tclproject.biomeidextender.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import net.tclproject.biomeidextender.asm.Name;

public class VanillaChunk implements IClassNodeTransformer {
   public void transform(ClassNode cn, boolean obfuscated) {
      MethodNode method = AsmUtil.findMethod(cn, Name.chunk_fillChunk, true);
      if (method != null) {
         this.transformFill(cn, method, obfuscated);
      }

      method = AsmUtil.findMethod(cn, Name.chunk_getBiomeGenForWorldCoords);
      AsmUtil.transformInlinedSizeMethod(cn, method, 255, 65535, false);
      ListIterator it = method.instructions.iterator();

      while(it.hasNext()) {
         AbstractInsnNode insn = (AbstractInsnNode)it.next();
         if (insn.getOpcode() == 180 && ((FieldInsnNode)insn).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
            it.set(Name.chunk_blockBiomeArray.getField(obfuscated));
         }

         if (insn.getOpcode() == 51) {
            it.set(new InsnNode(53));
         }

         if (insn.getOpcode() == 84) {
            it.set(new InsnNode(86));
         }
      }

      method = AsmUtil.findMethod(cn, Name.chunk_getBiomeArray);
      this.transformGetBiomeArray(cn, method, obfuscated);
      method = AsmUtil.findMethod(cn, Name.chunk_setBiomeArray);
      this.transformSetBiomeArray(cn, method, obfuscated);
      AsmUtil.findField(cn, Name.chunk_blockBiomeArray.getName(obfuscated)).desc = "[S";
      method = AsmUtil.findMethod(cn, Name.chunk_init);
      this.transformConstructor(cn, method, obfuscated);
      MethodNode getBiomeShortArray = new MethodNode(1, "getBiomeShortArray", "()[S", (String)null, (String[])null);
      MethodNode setBiomeShortArray = new MethodNode(1, "setBiomeShortArray", "([S)V", (String)null, (String[])null);
      cn.methods.add(getBiomeShortArray);
      cn.methods.add(setBiomeShortArray);
      this.fillGetBiomeShortArray(cn, getBiomeShortArray, obfuscated);
      this.fillSetBiomeShortArray(cn, setBiomeShortArray, obfuscated);

   }

   private void transformFill(ClassNode cn, MethodNode method, boolean obfuscated) {
      method.localVariables = null;
      int part = 0;
      ListIterator it = method.instructions.iterator();

      if (Loader.isModLoaded("notenoughIDs")) {
         while (true) {
            while (it.hasNext()) {
               AbstractInsnNode insn = (AbstractInsnNode) it.next();
               if (part == 0) {
                  if (insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21 && insn.getNext().getNext().getOpcode() == 25 && insn.getNext().getNext().getNext().getOpcode() == 180 && ((FieldInsnNode) insn.getNext().getNext().getNext()).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
                     it.remove();
                     ++part;
                  }
               } else if (part == 1) {
                  if (insn.getOpcode() == 184) {
                     it.remove();
                     it.next();
                     it.add(new VarInsnNode(21, 6));
                     it.add(new VarInsnNode(25, 0));
                     it.add(new VarInsnNode(25, 1));
                     it.add(new VarInsnNode(21, 6));
                     it.add(Name.hooks_readBiomeArrayFromPacket.staticInvocation(obfuscated));
                     it.add(new InsnNode(96));
                     it.add(new VarInsnNode(54, 6));
                     ++part;
                  } else {
                     it.remove();
                  }
               } else if (part == 2 && insn.getOpcode() == 180 && ((FieldInsnNode) insn).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
                  it.set(Name.chunk_blockBiomeArray.getField(obfuscated));
                  return;
               }
            }

            throw new AsmTransformException("no match for part " + part);
         }
      } else {
         int counter = 0;
         while (true) {
            while (it.hasNext()) {
               AbstractInsnNode insn = (AbstractInsnNode) it.next();
               if (part == 0) {
                  if (insn.getOpcode() == 182 && Name.ebs_getBlockLSBArray.matches((MethodInsnNode) insn, obfuscated)) {
                     it.set(new VarInsnNode(25, 1));
                     it.add(new VarInsnNode(21, 6));
                     it.add(Name.hooks_setBlockData.staticInvocation(obfuscated));
                     ++part;
                  }
               } else if (part == 1) {
                  if (insn.getOpcode() == 96) {
                     it.set(new VarInsnNode(21, 6));
                     it.add(new LdcInsnNode(8192));
                     it.add(new InsnNode(96));
                     ++part;
                  } else {
                     it.remove();
                  }
               } else if (part != 2) {
                  if (part == 3) {

                     ++part;
                  } else if (part == 4) {
                     counter++;
//                     System.out.println(counter);
//                     System.out.println(insn.getOpcode() == 25);
//                     System.out.println(insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21);
//                     System.out.println(insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21 && insn.getNext().getNext().getOpcode() == 25);
//                     System.out.println(insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21 && insn.getNext().getNext().getOpcode() == 25 && insn.getNext().getNext().getNext().getOpcode() == 180);
//                     System.out.println(insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21 && insn.getNext().getNext().getOpcode() == 25 && insn.getNext().getNext().getNext().getOpcode() == 180 && ((FieldInsnNode) insn.getNext().getNext().getNext()).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated)));
//                     System.out.println("----------------------***----------------------");
                     if (insn.getOpcode() == 25 && insn.getNext().getOpcode() == 21 && insn.getNext().getNext().getOpcode() == 25 && insn.getNext().getNext().getNext().getOpcode() == 180 && ((FieldInsnNode) insn.getNext().getNext().getNext()).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
                        it.remove();
                        ++part;
                     }
                  } else if (part == 5) {
                     if (insn.getOpcode() == 184) {
                        it.remove();
                        it.next();
                        it.add(new VarInsnNode(21, 6));
                        it.add(new VarInsnNode(25, 0));
                        it.add(new VarInsnNode(25, 1));
                        it.add(new VarInsnNode(21, 6));
                        it.add(Name.hooks_readBiomeArrayFromPacket.staticInvocation(obfuscated));
                        it.add(new InsnNode(96));
                        it.add(new VarInsnNode(54, 6));
                        ++part;
                     } else {
                        it.remove();
                     }
                  } else if (part == 6 && insn.getOpcode() == 180 && ((FieldInsnNode) insn).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
                     it.set(Name.chunk_blockBiomeArray.getField(obfuscated));
                     return;
                  }
               } else if (insn.getOpcode() == 182) {
                  MethodInsnNode node = (MethodInsnNode) insn;
                  if (Name.ebs_getBlockMSBArray.matches(node, obfuscated)) {
                     while (((AbstractInsnNode) it.previous()).getOpcode() != 162) {
                     }

                     insn = (AbstractInsnNode) it.next();
                     it.set(new JumpInsnNode(161, ((JumpInsnNode) insn).label));
                     ++part;
                  }
               }
            }

            throw new AsmTransformException("no match for part " + part);
         }
      }
   }

   private void transformGetBiomeArray(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(Name.hooks_getBiomeArray.staticInvocation(obfuscated));
      code.add(new InsnNode(176));
      method.localVariables = null;
      method.maxStack = 1;
   }

   private void transformSetBiomeArray(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      code.clear();
      code.add(new VarInsnNode(25, 0));
      code.add(new VarInsnNode(25, 1));
      code.add(Name.hooks_setBiomeArray.staticInvocation(obfuscated));
      code.add(Name.chunk_blockBiomeArray.putField(obfuscated));
      code.add(new InsnNode(177));
      method.instructions = code;
      method.localVariables = null;
      method.maxStack = 2;
   }

   private void fillGetBiomeShortArray(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      LabelNode l0 = new LabelNode();
      LabelNode l1 = new LabelNode();
      LineNumberNode lnn = new LineNumberNode(1438, l0);
      LocalVariableNode var = new LocalVariableNode("this", "Lnet/minecraft/world/chunk/Chunk;", (String)null, l0, l1, 0);
      code.add(l0);
      code.add(lnn);
      code.add(new VarInsnNode(25, 0));
      code.add(Name.chunk_blockBiomeArray.getField(obfuscated));
      code.add(new InsnNode(176));
      code.add(l1);
      method.localVariables.add(var);
      method.instructions = code;
      method.maxStack = 1;
      method.maxLocals = 1;
   }

   private void fillSetBiomeShortArray(ClassNode cn, MethodNode method, boolean obfuscated) {
      InsnList code = method.instructions;
      code.add(new VarInsnNode(25, 0));
      code.add(new VarInsnNode(25, 1));
      code.add(Name.chunk_blockBiomeArray.putField(obfuscated));
      code.add(new InsnNode(177));
      method.instructions = code;
      method.maxStack = 2;
      method.maxLocals = 2;
   }

   private void transformConstructor(ClassNode cn, MethodNode method, boolean obfuscated) {
      ListIterator it = method.instructions.iterator();

      while(it.hasNext()) {
         AbstractInsnNode insn = (AbstractInsnNode)it.next();
         if (insn.getOpcode() == 188 && ((IntInsnNode)insn).operand == 8) {
            System.out.println("Found, replaced!");
            it.set(new IntInsnNode(188, 9));
            it.next();
            it.set(Name.chunk_blockBiomeArray.putField(obfuscated));
         }

         if (insn.getOpcode() == 184 && ((MethodInsnNode)insn).desc.equals("([BB)V")) {
            it.set(new MethodInsnNode(184, "java/util/Arrays", "fill", "([SS)V", false));
         }

         if (insn.getOpcode() == 180 && ((FieldInsnNode)insn).name.equals(Name.chunk_blockBiomeArray.getName(obfuscated))) {
            it.set(Name.chunk_blockBiomeArray.getField(obfuscated));
         }
      }

   }
}
