package net.tclproject.biomeidextender;

import java.io.File;

import net.minecraft.util.EnumChatFormatting;



public class ModProperties {
// This class will be used to define mod properties in order to access them from anywhere.

    // General values
    public static final String MODID = "biomeidextender";
    public static final String NAME = "Biome ID Extender";
    public static final String VERSION = "1.1";
    public static final String MC_VERSION = "1.7.10";
    public static final String URL = "";
    public static final String VERSION_CHECKER_URL = "";


    // Mod info page
    public static final String COLORED_NAME = EnumChatFormatting.AQUA + "Biome ID" + EnumChatFormatting.BLUE + " Extender";

    public static final String COLORED_VERSION = EnumChatFormatting.GRAY + VERSION;
    public static final String COLORED_URL = EnumChatFormatting.GRAY + URL;

    public static final String CREDITS = "";

    public static final String[] AUTHORS = new String[] {
            EnumChatFormatting.DARK_PURPLE + "Matrix (TCLProject)",
            EnumChatFormatting.RED + "HRudyPlayZ"
    };

    public static final String DESCRIPTION = EnumChatFormatting.GRAY + "A mod that extends the " + EnumChatFormatting.AQUA + "biome id" + EnumChatFormatting.GRAY + " limit from 255 to 65535.";

    public static final String[] SPLASH_OF_THE_DAY = new String[] {
            "Darling, this is good!",
            "Why didn't someone do this sooner?",
            "Why Minecraft even has a biome id limit in the first place?",
            "Finally, someone did this!",
            "Why do i smell something burning?",
            "Only made possible by Notch's most realistic LEGO Simulator built so far.",
            "Please report any issue you find.",
            "Compatible with the finest of mods!",
            "As stable as my bank account!",
            "Compatibility is an honor... when things work.",
            "This is still an experimental mod, don't throw a nuclear missile on us :(",
            "Mitochondria is the powerhouse of the cell.",
            "And the best part is: it's not made in MCreator!",
            "Creeper? Awww man.",
            "Works great with Biomes O Plenty.",
            "Works great with Extra Biomes XL.",
            "Works great with Highlands.",
            "Also try AntiIDConflict.",
            "Who thought 256 biomes were enough?",
            "Finally released!",
            "\"This is highly experimental, any occurrences of a black hole, time portal or burning computer isn't our fault!\"",
            "Since you're here, you might want to support the mod on Curseforge!",
            "The most memes you can get, in one package.",
            "The revolution in modding history.",
            "Imagine a world, but without having to disable biomes!",
            "Why were the dwarves digging a hole? To get to this sooner!",
            "Elon Musk's hidden fetish.",
            "if thisModWorks() then thatsAwesome() else pleaseReportIssue() end",
            "Why did the chicken cross the road? Because this mod was waiting on the other side.",
            "Probably the cause of your ArrayOutOfBounds crashes.",
            "And i think to myself, what a wonderful world.",
            "It's like crypto but actually stable (we hope)!"
    };

    // Should be equal to null to disable it, otherwise it should just be the file name (ex: "logo.png").
    public static String LOGO = "assets/" + ModProperties.MODID + "/logo.png";
}
