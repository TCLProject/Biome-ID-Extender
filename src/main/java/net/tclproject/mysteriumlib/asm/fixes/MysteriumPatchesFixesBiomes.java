package net.tclproject.mysteriumlib.asm.fixes;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.util.ReportedException;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.tclproject.biomeidextender.asm.MainTransformer;
import net.tclproject.biomeidextender.utils.LogHelper;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;
import net.tclproject.mysteriumlib.asm.annotations.ReturnedValue;
import rtg.config.rtg.ConfigRTG;
import rtg.util.Logger;
import rtg.util.PlaneLocation;
import rtg.util.TimeTracker;
import rtg.world.biome.BiomeAnalyzer;
import rtg.world.biome.RTGBiomeProvider;
import rtg.world.biome.WorldChunkManagerRTG;
import rtg.world.biome.realistic.RealisticBiomeBase;
import rtg.world.biome.realistic.RealisticBiomePatcher;
import rtg.world.biome.realistic.vanilla.RealisticBiomeVanillaBase;
import rtg.world.gen.ChunkLandscape;
import rtg.world.gen.ChunkProviderRTG;
import rtg.world.gen.LandscapeGenerator;

import javax.management.ObjectName;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MysteriumPatchesFixesBiomes {

	public static void debug(String message) {
		System.out.println("[DEBUG] " + message);
	}

	public static void warn(String message) {
		System.out.println("[!!!WARNING!!!] " + message);
	}

	private static final MethodHandle biomeIndexLayerGet = createBiomeIndexLayerGet();
	private static final MethodHandle biomePatcherGet = createBiomePatcherGet();

	private static MethodHandle createBiomeIndexLayerGet() {
		try {
			Field field = WorldChunkManagerRTG.class.getDeclaredField("biomeIndexLayer");
			field.setAccessible(true);
			return MethodHandles.publicLookup().unreflectGetter(field);
		} catch (Exception e) {
			LogHelper.info("RTG is not installed. RTG Compatibility 1 will not be loaded.");
			return null;
		}
	}

	// ENHANCED BIOMES (EB) COMPAT ATTEMPT COMMENTED OUT
	//	private static final MethodHandle biomeIndexLayerEBGet = createBiomeIndexLayerEBGet();
//	private static MethodHandle createBiomeIndexLayerEBGet() {
//		try {
//			Field field = WorldChunkManagerEB.class.getDeclaredField("biomeIndexLayer");
//			field.setAccessible(true);
//			return MethodHandles.publicLookup().unreflectGetter(field);
//		} catch (Exception e) {
//			MainTransformer.logger.info("Enhanced Biomes is not installed. Enhanced Biomes Compatibility 2 will not be loaded.");
//			return null;
//		}
//	}


	private static MethodHandle createBiomePatcherGet() {
		try {
			Field field2 = WorldChunkManagerRTG.class.getDeclaredField("biomePatcher");
			field2.setAccessible(true);
			return MethodHandles.publicLookup().unreflectGetter(field2);
		} catch (Exception e) {
			LogHelper.info("RTG is not installed. RTG Compatibility 2 will not be loaded.");
			return null;
		}
	}

	// ENHANCED BIOMES (EB) COMPAT ATTEMPT COMMENTED OUT
//	@Optional.Method(modid="enhancedbiomes")
//	@Fix(returnSetting = EnumReturnSetting.ALWAYS)
//	public static float[] getRainfall(WorldChunkManagerEB cmeb, float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
//		IntCache.resetIntCache();
//		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
//			par1ArrayOfFloat = new float[par4 * par5];
//		}
//
//		GenLayer biomeIndexLayer = null;
//		try {
//			biomeIndexLayer = (GenLayer) biomeIndexLayerEBGet.invokeExact((WorldChunkManagerEB) cmeb);
//		} catch (Throwable e) {
//			throw new RuntimeException("Enhanced Biomes was present and loaded but compatibility module 2 backfired!", e);
//		}
//
//		int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);
//
//		for(int i1 = 0; i1 < par4 * par5; ++i1) {
//			try {
////				System.out.println("111111:    "+i1);
////				System.out.println("222222:    "+aint[i1]);
////				System.out.println("333333:    "+BiomeGenBase.getBiomeGenArray()[aint[i1]]);
//
//				float f = (float) (BiomeGenBase.getBiome(aint[i1]) == null ? BiomeGenBase.ocean : BiomeGenBase.getBiome(aint[i1])).getIntRainfall() / 65536.0F;
//				if (f > 1.0F) {
//					f = 1.0F;
//				}
//
//				par1ArrayOfFloat[i1] = f;
//			} catch (Throwable var11) {
//				CrashReport crashreport = CrashReport.makeCrashReport(var11, "Invalid Biome id");
//				CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
//				crashreportcategory.addCrashSection("biome id", i1);
//				crashreportcategory.addCrashSection("downfalls[] size", par1ArrayOfFloat.length);
//				crashreportcategory.addCrashSection("x", par2);
//				crashreportcategory.addCrashSection("z", par3);
//				crashreportcategory.addCrashSection("w", par4);
//				crashreportcategory.addCrashSection("h", par5);
//				throw new ReportedException(crashreport);
//			}
//		}
//
//		return par1ArrayOfFloat;
//	}

	@Optional.Method(modid="RTG")
	@Fix(returnSetting = EnumReturnSetting.ALWAYS)
	public static int getBiomeDataAt(LandscapeGenerator g, RTGBiomeProvider cmr, int worldX, int worldY) {
		int chunkX = worldX & 15;
		int chunkY = worldY & 15;
		ChunkLandscape target = g.landscape(cmr, worldX - chunkX, worldY - chunkY);
		if (target.biome[chunkX * 16 + chunkY] == null) {
			for (chunkY = 0;chunkY<16;++chunkY) {
				for (chunkX = 0;chunkX<16;++chunkX) {
					if (target.biome[chunkX * 16 + chunkY] != null) {
						return target.biome[chunkX * 16 + chunkY].baseBiome.biomeID;
					}
				}
			}
			LogHelper.error("Unable to get suitable biome! Temporarily defaulting to plains.");
			return 1;
		}
		return target.biome[chunkX * 16 + chunkY].baseBiome.biomeID;
	}

//	@Optional.Method(modid="RTG")
//	@Fix(returnSetting = EnumReturnSetting.ALWAYS)
//	public static Chunk provideChunk(ChunkProviderRTG rtgc, int cx, int cy) {
//		System.out.println("called!!!!!!!!!!!!!!!!");
//		PlaneLocation chunkLocation = new PlaneLocation.Invariant(cx, cy);
//		if (rtgc.inGeneration.containsKey(chunkLocation)) {
//			return (Chunk)rtgc.inGeneration.get(chunkLocation);
//		} else {
//			int k;
//			if (rtgc.chunkMade.contains(chunkLocation)) {
//				Chunk available = (Chunk)rtgc.availableChunks.get(chunkLocation);
//				if (available != null) {
//					List[] entityLists = available.entityLists;
//
//					for(k = 0; k < entityLists.length; ++k) {
//						Iterator iterator = entityLists[k].iterator();
//
//						while(iterator.hasNext()) {
//							iterator.next();
//							iterator.remove();
//						}
//
//						rtgc.worldObj.unloadEntities(entityLists[k]);
//					}
//
//					rtgc.toCheck.add(chunkLocation);
//					return available;
//				}
//			}
//
//			TimeTracker.manager.start(rtgc.rtgTerrain);
//			rtgc.rand.setSeed((long)cx * 341873128712L + (long)cy * 132897987541L);
//			Block[] blocks = new Block[65536];
//			byte[] metadata = new byte[65536];
//			ChunkLandscape landscape = rtgc.landscapeGenerator.landscape(rtgc.cmr, cx * 16, cy * 16);
//			rtgc.generateTerrain(rtgc.cmr, cx, cy, blocks, metadata, landscape.biome, landscape.noise);
//
//			for(int ci = 0; ci < 256; ++ci) {
//				rtgc.biomesGeneratedInChunk[landscape.biome[ci].baseBiome.biomeID] = true;
//			}
//
//			for(k = 0; k < 256; ++k) {
//				if (rtgc.biomesGeneratedInChunk[k]) {
//					RealisticBiomeBase.getBiome(k).generateMapGen(blocks, metadata, rtgc.worldSeed, rtgc.worldObj, rtgc.cmr, rtgc.mapRand, cx, cy, rtgc.simplex, rtgc.cell, landscape.noise);
//					rtgc.biomesGeneratedInChunk[k] = false;
//				}
//
//				try {
//					rtgc.baseBiomesList[k] = landscape.biome[k].baseBiome;
//				} catch (Exception var11) {
//					rtgc.baseBiomesList[k] = rtgc.biomePatcher.getPatchedBaseBiome("" + landscape.biome[k].baseBiome.biomeID);
//				}
//			}
//
//			rtgc.replaceBlocksForBiome(cx, cy, blocks, metadata, landscape.biome, rtgc.baseBiomesList, landscape.noise);
//			rtgc.caveGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//			rtgc.ravineGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//			if (rtgc.mapFeaturesEnabled) {
//				if (ConfigRTG.generateMineshafts) {
//					try {
//						rtgc.mineshaftGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//					} catch (Exception var15) {
//						if (ConfigRTG.crashOnStructureExceptions) {
//							throw new RuntimeException(var15.getMessage());
//						}
//
//						Logger.fatal(var15.getMessage(), var15);
//					}
//				}
//
//				if (ConfigRTG.generateStrongholds) {
//					try {
//						rtgc.strongholdGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//					} catch (Exception var14) {
//						if (ConfigRTG.crashOnStructureExceptions) {
//							throw new RuntimeException(var14.getMessage());
//						}
//
//						Logger.fatal(var14.getMessage(), var14);
//					}
//				}
//
//				if (ConfigRTG.generateVillages) {
//					try {
//						rtgc.villageGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//					} catch (Exception var13) {
//						if (ConfigRTG.crashOnStructureExceptions) {
//							throw new RuntimeException(var13.getMessage());
//						}
//
//						Logger.fatal(var13.getMessage(), var13);
//					}
//				}
//
//				if (ConfigRTG.generateScatteredFeatures) {
//					try {
//						rtgc.scatteredFeatureGenerator.func_151539_a(rtgc, rtgc.worldObj, cx, cy, blocks);
//					} catch (Exception var12) {
//						if (ConfigRTG.crashOnStructureExceptions) {
//							throw new RuntimeException(var12.getMessage());
//						}
//
//						Logger.fatal(var12.getMessage(), var12);
//					}
//				}
//			}
//
//			Chunk chunk = new Chunk(rtgc.worldObj, blocks, metadata, cx, cy);
//			rtgc.inGeneration.put(chunkLocation, chunk);
//			if (rtgc.isAICExtendingBiomeIdsLimit) {
//				rtgc.aic.setBiomeArray(chunk, rtgc.baseBiomesList, rtgc.xyinverted);
//			} else {
//				byte[] abyte1 = chunk.getBiomeArray();
//
//				for(k = 0; k < abyte1.length; ++k) {
//					byte b = (byte)rtgc.baseBiomesList[rtgc.xyinverted[k]].biomeID;
//					abyte1[k] = b;
//				}
//
//				chunk.setBiomeArray(abyte1);
//			}
//
//			chunk.generateSkylightMap();
//			rtgc.toCheck.add(chunkLocation);
//			rtgc.inGeneration.remove(chunkLocation);
//			rtgc.chunkMade.add(chunkLocation);
//			rtgc.availableChunks.put(chunkLocation, chunk);
//			TimeTracker.manager.stop(rtgc.rtgTerrain);
//			return chunk;
//		}
//	}

	@Optional.Method(modid="RTG")
	@Fix(insertOnExit = true, returnSetting = EnumReturnSetting.ALWAYS)
	public static synchronized ChunkLandscape landscape(LandscapeGenerator lg, RTGBiomeProvider cmr, int worldX, int worldY, @ReturnedValue ChunkLandscape oldValue) {
		RealisticBiomeBase cache = null;
		boolean[] nullvalues = new boolean[256];
		for (int j = 0;j<256;++j) {
			if (oldValue.biome[j] != null) {
				cache = oldValue.biome[j];
			}
		}
		for (int chunkY = 0;chunkY<16;++chunkY) {
			for (int chunkX = 0;chunkX<16;++chunkX) {
				if (oldValue.biome[chunkX * 16 + chunkY] != null) {
					cache = oldValue.biome[chunkX * 16 + chunkY];
				} else {
					if (cache != null) {
						oldValue.biome[chunkX * 16 + chunkY] = cache;
					} else {
						nullvalues[chunkX * 16 + chunkY] = true;
					}
				}
			}
		}
		for (int i = 0; i < nullvalues.length; i++) {
			if (nullvalues[i]) {
				oldValue.biome[i] = (cache == null ? RealisticBiomeVanillaBase.vanillaPlains : cache);
			}
		}
		return oldValue;
	}

	@Optional.Method(modid="RTG")
	@Fix(returnSetting = EnumReturnSetting.ALWAYS)
	public static float[] getRainfall(WorldChunkManagerRTG wcmrtg, float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
		IntCache.resetIntCache();
		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
			par1ArrayOfFloat = new float[par4 * par5];
		}

		GenLayer biomeIndexLayer = null;
		try {
			biomeIndexLayer = (GenLayer) biomeIndexLayerGet.invokeExact((WorldChunkManagerRTG) wcmrtg);
		} catch (Throwable e) {
			throw new RuntimeException("RTG was present and loaded but compatibility module 1 backfired!", e);
		}
		int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

		for(int i1 = 0; i1 < par4 * par5; ++i1) {
			float f = 0.0F;
			int biome = aint[i1];

			try {

				f = (float) RealisticBiomeBase.getBiome(biome).baseBiome.getIntRainfall() / 65536.0F;
			} catch (Exception var11) {

				if (RealisticBiomeBase.getBiome(biome) == null) {

					RealisticBiomePatcher biomePatcher = null;
					try {
						biomePatcher = (RealisticBiomePatcher) biomePatcherGet.invokeExact((WorldChunkManagerRTG) wcmrtg);
					} catch (Throwable e) {
						throw new RuntimeException("RTG was present and loaded but compatibility module 1 backfired!", e);
					}

					f = (float)biomePatcher.getPatchedRealisticBiome("Problem with biome " + biome + " from " + var11.getMessage()).baseBiome.getIntRainfall() / 65536.0F;
				}
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			par1ArrayOfFloat[i1] = f;
		}

		return par1ArrayOfFloat;
	}
	
//	@Fix
//	public static void runGameLoop(Minecraft mc)
//    {
//		synchronized (PortUtil.getMinecraft().scheduledTasks)
//        {
//            while (!PortUtil.getMinecraft().scheduledTasks.isEmpty())
//            {
//            	PortUtil.runTask(PortUtil.getMinecraft().scheduledTasks.poll(), LOGGER);
//            }
//        }
//    }

}
