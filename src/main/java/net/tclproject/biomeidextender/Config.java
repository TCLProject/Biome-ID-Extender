package net.tclproject.biomeidextender;

import net.minecraftforge.common.config.Configuration;

import net.tclproject.biomeidextender.utils.LogHelper;

import java.io.File;

public class Config {
    public static String configFolder = "config" + File.separator;

    public static boolean debugMode;

    public static void createConfigFile() {
        // Creates the config file using Forge's system to allow to configure some stuff in the mod.

        LogHelper.info("Generating the config file...");

        File path = new File(configFolder + "biomeidextender.cfg");
        Configuration config = new Configuration(path);
        config.load();

        debugMode = config.getBoolean("enableDebugMode", "general", false, "Enable this to enable the ID logger and debug issues.");

        config.save();
    }

}
