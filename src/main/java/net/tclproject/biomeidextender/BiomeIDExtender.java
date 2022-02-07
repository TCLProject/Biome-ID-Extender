package net.tclproject.biomeidextender;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import rtg.world.biome.realistic.RealisticBiomeBase;

@Mod(
   modid = "biomeidextender",
   name = "Biome ID Extender",
   version = "1.0a"
)
public class BiomeIDExtender {

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
   }
   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
   }

   @SubscribeEvent
   public void handlePlayerTick(TickEvent.PlayerTickEvent event) {
//      System.out.println(RealisticBiomeBase.getBiome(3103).baseBiome.biomeName);
//      System.out.println(event.player.worldObj.isRemote + "," + event.player.worldObj.getBiomeGenForCoords((int)event.player.posX, (int)event.player.posZ).biomeID);
   }

}
