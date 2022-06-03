package net.tclproject.biomeidextender.asm;

import cpw.mods.fml.common.Loader;
import net.minecraft.launchwrapper.IClassTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

public class MainTransformer implements IClassTransformer {
   private static final boolean enablePreVerification = false;
   private static final boolean enablePostVerification = true;
   private static Boolean isObfuscated = null;
   private static Boolean isClient = null;

   public byte[] transform(String name, String transformedName, byte[] bytes) {
      if (bytes == null) return bytes;
      else {
         if (Loader.isModLoaded("notenoughIDs")) {
            if ("net.minecraft.world.chunk.storage.ExtendedBlockStorage".equalsIgnoreCase(transformedName)) {
               LogHelper.info("Skipping ExtendedBlockStorage transforms as NotEnoughIDs is installed.");
               return bytes;
            }

            if ("net.minecraft.client.network.NetHandlerPlayClient".equalsIgnoreCase(transformedName)) {
               LogHelper.info("Skipping NetHandlerPlayClient transforms as NotEnoughIDs is installed.");
               return bytes;
            }
         }

         ClassEdit edit = ClassEdit.get(transformedName);
         if (edit == null) return bytes;
         else {
            LogHelper.info(LogHelper.format("Patching %s with %s...", transformedName, edit.getName()));
            ClassNode cn = new ClassNode(327680);
            ClassReader reader = new ClassReader(bytes);
            boolean readFlags = false;
            reader.accept(cn, 0);

            try {
               edit.getTransformer().transform(cn, isObfuscated());
            } catch (AsmTransformException var11) {
               LogHelper.error(LogHelper.format("ASM Error transforming %s with %s: %s", transformedName, edit.getName(), var11.getMessage()));
               throw var11;
            } catch (Throwable var12) {
               LogHelper.error(LogHelper.format("Throwable Error transforming %s with %s: %s", transformedName, edit.getName(), var12.getMessage()));
               throw new RuntimeException(var12);
            }

            ClassWriter writer = new ClassWriter(0);

            try {
               ClassVisitor check = new CheckClassAdapter(writer);
               cn.accept(check);
            } catch (Throwable var10) {
               LogHelper.error(LogHelper.format("Error verifying %s transformed with %s: %s", transformedName, edit.getName(), var10.getMessage()));
               throw new RuntimeException(var10);
            }

            LogHelper.info(LogHelper.format("Patched %s successfully.", edit.getName()));
            return writer.toByteArray();
         }
      }

   }

   public static boolean isClient() {
      if (isClient == null) isClient = MainTransformer.class.getResource("/net/minecraft/client/main/Main.class") != null;
      return isClient;
   }

   private static boolean isObfuscated() {
      if (isObfuscated == null) isObfuscated = MainTransformer.class.getResource("/net/minecraft/world/World.class") == null;
      return isObfuscated;
   }

}
