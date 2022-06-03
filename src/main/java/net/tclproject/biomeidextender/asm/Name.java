package net.tclproject.biomeidextender.asm;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public enum Name {
   hooks("net/tclproject/biomeidextender/BiomeUtils"),
   hooks_fromShortToByteArray(hooks, "fromShortToByteArray", null, null, "([S)[B"),
   hooks_readBiomeArrayFromPacket(hooks, "readBiomeArrayFromPacket", null, null, "(Lnet/minecraft/world/chunk/Chunk;[BI)I"),
   hooks_writeBiomeArrayToPacket(hooks, "writeBiomeArrayToPacket", null, null, "(Lnet/minecraft/world/chunk/Chunk;[BI)I"),
   hooks_getBiomeArray(hooks, "getBiomeArray", null, null, "(Lnet/minecraft/world/chunk/Chunk;)[B"),
   hooks_setBiomeArray(hooks, "setBiomeArray", null, null, "([B)[S"),
   hooks_create16BArray(hooks, "create16BArray", null, null, "()[S"),
   hooks_getBlockData(hooks, "getBlockData", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)[B"),
   hooks_setBlockData(hooks, "setBlockData", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;[BI)V"),
   hooks_writeChunkBiomeArrayToNbt(hooks, "writeChunkBiomeArrayToNbt", null, null, "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/nbt/NBTTagCompound;)V"),
   hooks_readChunkBiomeArrayFromNbt(hooks, "readChunkBiomeArrayFromNbt", null, null, "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/nbt/NBTTagCompound;)V"),
   hooks_writeChunkToNbt(hooks, "writeChunkToNbt", null, null, "(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V"),
   hooks_readChunkFromNbt(hooks, "readChunkFromNbt", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;Lnet/minecraft/nbt/NBTTagCompound;)V"),
   hooks_getBlockId(hooks, "getBlockId", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;III)I"),
   hooks_getBlockById(hooks, "getBlock", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;III)Lnet/minecraft/block/Block;"),
   hooks_setBlockId(hooks, "setBlockId", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;IIII)V"),
   biomeGenBase("net/minecraft/world/biome/BiomeGenBase", "ahu"),
   worldChunkManager("net/minecraft/world/biome/WorldChunkManager", "aib"),
   acl("net/minecraft/world/chunk/storage/AnvilChunkLoader", "aqk"),
   block("net/minecraft/block/Block", "aji"),
   chunk("net/minecraft/world/chunk/Chunk", "apx"),
   anyChunkProvider("net/minecraft/world/gen/ChunkProviderGenerate", "aqz"),
   extendedBlockStorage("net/minecraft/world/chunk/storage/ExtendedBlockStorage", "apz"),
   iChunkProvider("net/minecraft/world/chunk/IChunkProvider", "apu"),
   nbtTagCompound("net/minecraft/nbt/NBTTagCompound", "dh"),
   nhpc("net/minecraft/client/network/NetHandlerPlayClient", "bjb"),
   nibbleArray("net/minecraft/world/chunk/NibbleArray", "apv"),
   packet("net/minecraft/network/Packet", "ft"),
   packetBuffer("net/minecraft/network/PacketBuffer", "et"),
   s22("net/minecraft/network/play/server/S22PacketMultiBlockChange", "gk"),
   s21("net/minecraft/network/play/server/S21PacketChunkData", "gx"),
   s21_extracted("net/minecraft/network/play/server/S21PacketChunkData$Extracted", "gy"),
   world("net/minecraft/world/World", "ahb"),
   genLayer("GenLayerRiverMix", "axv"),
   ebs_getBlock(extendedBlockStorage, "getBlockByExtId", "a", "func_150819_a", "(III)Lnet/minecraft/block/Block;"),
   ebs_setBlock(extendedBlockStorage, "func_150818_a", "a", null, "(IIILnet/minecraft/block/Block;)V"),
   ebs_getBlockLSBArray(extendedBlockStorage, "getBlockLSBArray", "g", "func_76658_g", "()[B"),
   ebs_getBlockMSBArray(extendedBlockStorage, "getBlockMSBArray", "i", "func_76660_i", "()Lnet/minecraft/world/chunk/NibbleArray;"),
   ebs_setBlockMSBArray(extendedBlockStorage, "setBlockMSBArray", "a", "func_76673_a", "(Lnet/minecraft/world/chunk/NibbleArray;)V"),
   ebs_isEmpty(extendedBlockStorage, "isEmpty", "a", "func_76663_a", "()Z"),
   ebs_removeInvalidBlocks(extendedBlockStorage, "removeInvalidBlocks", "e", "func_76672_e", "()V"),
   ebs_blockRefCount(extendedBlockStorage, "blockRefCount", "b", "field_76682_b", "I"),
   ebs_tickRefCount(extendedBlockStorage, "tickRefCount", "c", "field_76683_c", "I"),
   acl_writeChunkToNBT(acl, "writeChunkToNBT", "a", "func_75820_a", "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/World;Lnet/minecraft/nbt/NBTTagCompound;)V"),
   acl_readChunkFromNBT(acl, "readChunkFromNBT", "a", "func_75823_a", "(Lnet/minecraft/world/World;Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/world/chunk/Chunk;"),
   block_getIdFromBlock(block, "getIdFromBlock", "b", "func_149682_b", "(Lnet/minecraft/block/Block;)I"),
   chunk_fillChunk(chunk, "fillChunk", "a", "func_76607_a", "([BIIZ)V"),
   chunk_getBiomeGenForWorldCoords(chunk, "getBiomeGenForWorldCoords", "a", "func_76591_a", "(IILnet/minecraft/world/biome/WorldChunkManager;)Lnet/minecraft/world/biome/BiomeGenBase;"),
   chunk_getBiomeArray(chunk, "getBiomeArray", "m", "func_76605_m", "()[B"),
   chunk_setBiomeArray(chunk, "setBiomeArray", "a", "func_76616_a", "([B)V"),
   chunk_getBiomeShortArray(chunk, "getBiomeShortArray", null, null, "()[S"),
   chunk_setBiomeShortArray(chunk, "setBiomeShortArray", null, null, "([S)V"),
   chunk_blockBiomeArray(chunk, "blockBiomeArray", "v", "field_76651_r", "[S"),
   chunk_init(chunk, "<init>", null, null, "(Lnet/minecraft/world/World;II)V"),
   genLayer_geiInts(genLayer, "getInts", "a", "func_75904_a", "(IIII)[I"),
   anyChunkProvider_provideChunk(anyChunkProvider, "provideChunk", "d", "func_73154_d", "(II)Lnet/minecraft/world/chunk/Chunk;"),
   packet_readPacketData(packet, "readPacketData", "a", "func_148837_a", "(Lnet/minecraft/network/PacketBuffer;)V"),
   nhpc_handleMultiBlockChange(nhpc, "handleMultiBlockChange", "a", "func_147287_a", "(Lnet/minecraft/network/play/server/S22PacketMultiBlockChange;)V"),
   s22_init_server(s22, "<init>", null, null, "(I[SLnet/minecraft/world/chunk/Chunk;)V"),
   s21_undefined1(s21, "func_149275_c", "c", null, "()I"),
   s21_undefined2(s21, "func_149269_a", "a", null, "(Lnet/minecraft/world/chunk/Chunk;ZI)Lnet/minecraft/network/play/server/S21PacketChunkData$Extracted;"),
   ub_bud("exterminatorJeff/undergroundBiomes/worldGen/BiomeUndergroundDecorator"),
   ub_bud_replaceChunkOres_world(ub_bud, "replaceChunkOres", null, null, "(IILnet/minecraft/world/World;)V"),
   ub_bud_replaceChunkOres_iChunkProvider(ub_bud, "replaceChunkOres", null, null, "(Lnet/minecraft/world/chunk/IChunkProvider;II)V");

   public final Name clazz;
   public final String deobf;
   public final String obf;
   public final String srg;
   public final String desc;
   public String obfDesc;

   Name(String deobf) {
      this(deobf, deobf);
   }

   Name(String deobf, String obf) {
      this.clazz = null;
      this.deobf = deobf;
      this.obf = obf;
      this.srg = deobf;
      this.desc = null;
   }

   Name(Name clazz, String deobf, String obf, String srg, String desc) {
      this.clazz = clazz;
      this.deobf = deobf;
      this.obf = obf != null ? obf : deobf;
      this.srg = srg != null ? srg : deobf;
      this.desc = desc;
   }

   public boolean matches(MethodNode x) {
      assert this.desc.startsWith("(");

      return this.obf.equals(x.name) && this.obfDesc.equals(x.desc) || this.srg.equals(x.name) && this.desc.equals(x.desc) || this.deobf.equals(x.name) && this.desc.equals(x.desc);
   }

   public boolean matches(FieldNode x) {
      assert !this.desc.startsWith("(");

      return this.obf.equals(x.name) && this.obfDesc.equals(x.desc) || this.srg.equals(x.name) && this.desc.equals(x.desc) || this.deobf.equals(x.name) && this.desc.equals(x.desc);
   }

   public boolean matches(MethodInsnNode x, boolean obfuscated) {
      assert this.desc.startsWith("(");

      if (obfuscated) {
         return this.clazz.obf.equals(x.owner) && this.obf.equals(x.name) && this.obfDesc.equals(x.desc) || this.clazz.srg.equals(x.owner) && this.srg.equals(x.name) && this.desc.equals(x.desc);
      } else {
         return this.clazz.deobf.equals(x.owner) && this.deobf.equals(x.name) && this.desc.equals(x.desc);
      }
   }

   public boolean matches(FieldInsnNode x, boolean obfuscated) {
      assert !this.desc.startsWith("(");

      if (obfuscated) {
         return this.clazz.obf.equals(x.owner) && this.obf.equals(x.name) && this.obfDesc.equals(x.desc) || this.clazz.srg.equals(x.owner) && this.srg.equals(x.name) && this.desc.equals(x.desc);
      } else {
         return this.clazz.deobf.equals(x.owner) && this.deobf.equals(x.name) && this.desc.equals(x.desc);
      }
   }

   public MethodInsnNode staticInvocation(boolean obfuscated) {
      assert this.desc.startsWith("(");

      return obfuscated ? new MethodInsnNode(184, this.clazz.srg, this.srg, this.desc, false) : new MethodInsnNode(184, this.clazz.deobf, this.deobf, this.desc, false);
   }

   public FieldInsnNode putField(boolean obfuscated) {
      return obfuscated ? new FieldInsnNode(181, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(181, this.clazz.deobf, this.deobf, this.desc);
   }

   public FieldInsnNode getField(boolean obfuscated) {
      return obfuscated ? new FieldInsnNode(180, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(180, this.clazz.deobf, this.deobf, this.desc);
   }

   public MethodInsnNode virtualInvocation(boolean obfuscated) {
      assert this.desc.startsWith("(");

      return obfuscated ? new MethodInsnNode(182, this.clazz.srg, this.srg, this.desc, false) : new MethodInsnNode(182, this.clazz.deobf, this.deobf, this.desc, false);
   }

   public FieldInsnNode staticGet(boolean obfuscated) {
      assert !this.desc.startsWith("(");

      return obfuscated ? new FieldInsnNode(178, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(178, this.clazz.deobf, this.deobf, this.desc);
   }

   public FieldInsnNode virtualGet(boolean obfuscated) {
      assert !this.desc.startsWith("(");

      return obfuscated ? new FieldInsnNode(180, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(180, this.clazz.deobf, this.deobf, this.desc);
   }

   public FieldInsnNode staticSet(boolean obfuscated) {
      assert !this.desc.startsWith("(");

      return obfuscated ? new FieldInsnNode(179, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(179, this.clazz.deobf, this.deobf, this.desc);
   }

   public FieldInsnNode virtualSet(boolean obfuscated) {
      assert !this.desc.startsWith("(");

      return obfuscated ? new FieldInsnNode(181, this.clazz.srg, this.srg, this.desc) : new FieldInsnNode(181, this.clazz.deobf, this.deobf, this.desc);
   }

   private static void translateDescs() {
      StringBuilder sb = new StringBuilder();
      Name[] var1 = values();

      for (Name name : var1) {
         if (name.desc != null) {
            int pos = 0;

            int endPos;
            for (endPos = -1; (pos = name.desc.indexOf(76, pos)) != -1; pos = endPos + 1) {
               sb.append(name.desc, endPos + 1, pos);
               endPos = name.desc.indexOf(59, pos + 1);
               String cName = name.desc.substring(pos + 1, endPos);
               Name[] var8 = values();

               for (Name name2 : var8) {
                  if (name2.deobf.equals(cName)) {
                     cName = name2.obf;
                     break;
                  }
               }

               sb.append('L');
               sb.append(cName);
               sb.append(';');
            }

            sb.append(name.desc, endPos + 1, name.desc.length());
            name.obfDesc = sb.toString();
            sb.setLength(0);
         }
      }

   }

   public String getName(boolean obf) {
      return obf ? this.srg : this.deobf;
   }

   public String getDesc(boolean obf) {
      return obf ? this.obfDesc : this.desc;
   }

   static {
      translateDescs();
   }
}
