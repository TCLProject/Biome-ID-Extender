package net.tclproject.biomeidextender.asm;

import java.util.HashMap;
import java.util.Map;

import net.tclproject.biomeidextender.asm.transformer.*;
import net.tclproject.biomeidextender.asm.transformer.ATG.*;
import net.tclproject.biomeidextender.asm.transformer.AntiIDConflict.*;
import net.tclproject.biomeidextender.asm.transformer.BiomesOPlenty.*;
import net.tclproject.biomeidextender.asm.transformer.DragonAPI.*;
import net.tclproject.biomeidextender.asm.transformer.RTG.*;
import net.tclproject.biomeidextender.asm.transformer.Vanilla.*;

public enum ClassEdit {

   SelfHooks(new SelfHooks(), "net.tclproject.biomeidextender.BiomeUtils"),

   VanillaAnvilChunkLoader(new VanillaAnvilChunkLoader(), "net.minecraft.world.chunk.storage.AnvilChunkLoader"),
   VanillaChunk(new VanillaChunk(), "net.minecraft.world.chunk.Chunk"),
   VanillaExtendedBlockStorage(new VanillaExtendedBlockStorage(), "net.minecraft.world.chunk.storage.ExtendedBlockStorage"),
   VanillaNetHandlerPlayClient(new VanillaNetHandlerPlayClient(), "net.minecraft.client.network.NetHandlerPlayClient"),
   VanillaS21PacketChunkData(new VanillaS21PacketChunkData(), "net.minecraft.network.play.server.S21PacketChunkData"),
   VanillaChunkProviders(new VanillaChunkProvider(), "net.minecraft.world.gen.ChunkProviderEnd", "net.minecraft.world.gen.ChunkProviderFlat", "net.minecraft.world.gen.ChunkProviderGenerate", "net.minecraft.world.gen.ChunkProviderHell"),
   VanillaBiomeGenBase(new VanillaBiomeGenBase(), "net.minecraft.world.biome.BiomeGenBase"),
   VanillaGenLayes(new VanillaGenLayes(), "net.minecraft.world.gen.layer.GenLayerRiverMix", "net.minecraft.world.gen.layer.GenLayerVoronoiZoom", "biomesoplenty.common.world.layer.GenLayerRiverMixBOP"),
   VanillaS26PacketMapChunkBulk(new VanillaS26PacketMapChunkBulk(), "net.minecraft.network.play.server.S26PacketMapChunkBulk"),

   BoPBiomeManager(new BoPBiomeManager(), "biomesoplenty.common.world.BOPBiomeManager"),

   ATGBiomeManager(new ATGBiomeManager(), "ttftcuts.atg.gen.ATGBiomeManager"),
   ATGBiomeConfig(new ATGBiomeConfig(), "ttftcuts.atg.config.configfiles.ATGBiomeConfig"),
   ATGWorldGenRocks(new ATGWorldGenRocks(), "ttftcuts.atg.feature.ATGWorldGenRocks"),

   RTGLandscapeGenerator(new RTGLandscapeGenerator(), "rtg.world.gen.LandscapeGenerator"),
   RTGChunkProvider(new RTGChunkProvider(), "rtg.world.gen.ChunkProviderRTG"),
   RTGRealisticBiomeBase(new RTGRealisticBiomeBase(), "rtg.world.biome.realistic.thaumcraft.RealisticBiomeTCBase",
           "rtg.world.biome.realistic.ridiculousworld.RealisticBiomeRWBase",
           "rtg.world.biome.realistic.enhancedbiomes.RealisticBiomeEBBase",
           "rtg.world.biome.realistic.atg.RealisticBiomeATGBase",
           "rtg.world.biome.realistic.chromaticraft.RealisticBiomeCCBase"),

   DAIDType(new DAIDType(), "Reika.DragonAPI.Extras.IDType"),

   ConflictingBiomes(new ConflictingBiomes(), "code.elix_x.coremods.antiidconflict.managers.BiomesManager$ConflictingBiomes")

   ;


   private static final Map editMap = new HashMap();
   private final IClassNodeTransformer transformer;
   private final String[] classNames;

   ClassEdit(IClassNodeTransformer transformer, String... classNames) {
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

      for (ClassEdit edit : var0) {
         for (String name : edit.classNames) editMap.put(name, edit);
      }
   }

}
