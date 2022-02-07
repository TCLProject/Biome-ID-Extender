package net.tclproject.biomeidextender.asm;

import java.util.HashMap;
import java.util.Map;
import net.tclproject.biomeidextender.asm.transformer.*;

public enum ClassEdit {

   SelfHooks(new SelfHooks(), new String[]{"net.tclproject.biomeidextender.BiomeUtils"}),
   VanillaAnvilChunkLoader(new VanillaAnvilChunkLoader(), new String[]{"net.minecraft.world.chunk.storage.AnvilChunkLoader"}),
   VanillaChunk(new VanillaChunk(), new String[]{"net.minecraft.world.chunk.Chunk"}),
   VanillaExtendedBlockStorage(new VanillaExtendedBlockStorage(), new String[]{"net.minecraft.world.chunk.storage.ExtendedBlockStorage"}),
   VanillaNetHandlerPlayClient(new VanillaNetHandlerPlayClient(), new String[]{"net.minecraft.client.network.NetHandlerPlayClient"}),
   VanillaS21PacketChunkData(new VanillaS21PacketChunkData(), new String[]{"net.minecraft.network.play.server.S21PacketChunkData"}),
   VanillaChunkProviders(new VanillaChunkProvider(), new String[]{"net.minecraft.world.gen.ChunkProviderEnd", "net.minecraft.world.gen.ChunkProviderFlat", "net.minecraft.world.gen.ChunkProviderGenerate", "net.minecraft.world.gen.ChunkProviderHell"}),
   VanillaBiomeGenBase(new VanillaBiomeGenBase(), new String[]{"net.minecraft.world.biome.BiomeGenBase"}),
   VanillaGenLayes(new VanillaGenLayes(), new String[]{"net.minecraft.world.gen.layer.GenLayerRiverMix", "net.minecraft.world.gen.layer.GenLayerVoronoiZoom", "biomesoplenty.common.world.layer.GenLayerRiverMixBOP"}),
   BoPBiomeManager(new BoPBiomeManager(), new String[]{"biomesoplenty.common.world.BOPBiomeManager"}),
   ATGBiomeManager(new ATGBiomeManager(), new String[]{"ttftcuts.atg.gen.ATGBiomeManager"}),
   ATGBiomeConfig(new ATGBiomeConfig(), new String[]{"ttftcuts.atg.config.configfiles.ATGBiomeConfig"}),
   RTGLandscapeGenerator(new RTGLandscapeGenerator(), new String[]{"rtg.world.gen.LandscapeGenerator"}),
   RTGChunkProvider(new RTGChunkProvider(), new String[]{"rtg.world.gen.ChunkProviderRTG"}),
   DAIDType(new DAIDType(), new String[]{"Reika.DragonAPI.Extras.IDType"}),
   VanillaS26PacketMapChunkBulk(new VanillaS26PacketMapChunkBulk(), new String[]{"net.minecraft.network.play.server.S26PacketMapChunkBulk"}),
   ATGWorldGenRocks(new ATGWorldGenRocks(), new String[]{"ttftcuts.atg.feature.ATGWorldGenRocks"}),
   EBGenLayerHills(new EBGenLayerHills(), new String[]{"enhancedbiomes.world.gen.layer.GenLayerEBHills", "enhancedbiomes.world.gen.layer.GenLayerEBRiverMix"}),
   RTGRealisticBiomeBase(new RTGRealisticBiomeBase(), new String[]{
           "rtg.world.biome.realistic.thaumcraft.RealisticBiomeTCBase",
           "rtg.world.biome.realistic.ridiculousworld.RealisticBiomeRWBase",
           "rtg.world.biome.realistic.enhancedbiomes.RealisticBiomeEBBase",
           "rtg.world.biome.realistic.atg.RealisticBiomeATGBase",
           "rtg.world.biome.realistic.chromaticraft.RealisticBiomeCCBase"});

   private static final Map editMap = new HashMap();
   private final IClassNodeTransformer transformer;
   private final String[] classNames;

   private ClassEdit(IClassNodeTransformer transformer, String... classNames) {
      this.transformer = transformer;
      this.classNames = classNames;
   }

   public static ClassEdit get(String className) {
      return (ClassEdit)editMap.get(className);
   }

   public String getName() {
      return this.name();
   }

   public IClassNodeTransformer getTransformer() {
      return this.transformer;
   }

   static {
      ClassEdit[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         ClassEdit edit = var0[var2];
         String[] var4 = edit.classNames;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String name = var4[var6];
            editMap.put(name, edit);
         }
      }

   }
}
