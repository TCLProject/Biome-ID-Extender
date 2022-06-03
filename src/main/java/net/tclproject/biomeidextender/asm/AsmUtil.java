package net.tclproject.biomeidextender.asm;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

public class AsmUtil {
   public static MethodNode findMethod(ClassNode cn, String name) {
      return findMethod(cn, name, null, false);
   }

   public static MethodNode findMethod(ClassNode cn, String name, String desc) {
      return findMethod(cn, name, desc, false);
   }

   public static MethodNode findMethod(ClassNode cn, String name, String desc, boolean optional) {

      for (MethodNode ret : cn.methods) {
         if (ret.name.equals(name)) {
            if (desc == null) return ret;

            if (ret.desc.equals(desc)) return ret;
         }
      }

      if (optional) return null;
      else throw new MethodNotFoundException(name, desc);
   }

   public static MethodNode findMethod(ClassNode cn, Name name) {
      return findMethod(cn, name, false);
   }

   public static MethodNode findMethod(ClassNode cn, Name name, boolean optional) {
      Iterator<MethodNode> var3 = cn.methods.iterator();

      MethodNode ret;
      do {
         if (!var3.hasNext()) {
            if (optional) return null;

            throw new MethodNotFoundException(name.deobf);
         }

         ret = var3.next();
      } while(!name.matches(ret));

      return ret;
   }

   public static FieldNode findField(ClassNode cn, String name) {
      return findField(cn, name, false);
   }

   public static FieldNode findField(ClassNode cn, String name, boolean optional) {
      Iterator<FieldNode> var3 = cn.fields.iterator();

      FieldNode ret;
      do {
         if (!var3.hasNext()) {
            if (optional) return null;

            throw new FieldNotFoundException(name);
         }

         ret = var3.next();
      } while(!name.equals(ret.name));

      return ret;
   }

   public static FieldNode findField(ClassNode cn, Name name) {
      return findField(cn, name, false);
   }

   public static FieldNode findField(ClassNode cn, Name name, boolean optional) {
      Iterator<FieldNode> var3 = cn.fields.iterator();

      FieldNode ret;
      do {
         if (!var3.hasNext()) {
            if (optional) return null;

            throw new FieldNotFoundException(name.deobf);
         }

         ret = var3.next();
      } while(!name.matches(ret));

      return ret;
   }

   public static void makePublic(MethodNode x) {
      x.access = x.access & -7 | 1;
   }

   public static void makePublic(FieldNode x) {
      x.access = x.access & -7 | 1;
   }

   public static boolean transformInlinedSizeMethod(ClassNode cn, MethodNode method, int oldValue, int newValue, boolean optional) {
      boolean found = false;
      ListIterator<AbstractInsnNode> it = method.instructions.iterator();


      while(it.hasNext()) {
         AbstractInsnNode insn = it.next();
         if (insn.getOpcode() == 18) {
            LdcInsnNode node = (LdcInsnNode)insn;

            if (node.cst instanceof Integer && (Integer)node.cst == oldValue) {
               node.cst = newValue;
               found = true;
            }
         }
         else if (insn.getOpcode() == 17 || insn.getOpcode() == 16) {
            IntInsnNode node = (IntInsnNode)insn;

            if (node.operand == oldValue) {
               if ((newValue < -128 || newValue > 127) && (insn.getOpcode() != 17 || newValue < -32768 || newValue > 32767)) it.set(new LdcInsnNode(newValue));
               else node.operand = newValue;

               found = true;
            }

         }
      }

      if (!found && !optional) throw new AsmTransformException("can't find constant value " + oldValue + " in method " + method.name);

      return found;
   }

   public static void dump(InsnList list) {
      Textifier textifier = new Textifier();
      TraceMethodVisitor visitor = new TraceMethodVisitor(textifier);
      list.accept(visitor);
      PrintWriter writer = new PrintWriter(System.out);
      textifier.print(writer);
      writer.flush();
   }
}
