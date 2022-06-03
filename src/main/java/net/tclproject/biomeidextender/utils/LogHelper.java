package net.tclproject.biomeidextender.utils;

import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.FMLLog;
import net.tclproject.biomeidextender.ModProperties;

import java.util.Formatter;

public class LogHelper {
    public static void log(Level level, Object object) {
        // Prints a certain object (probably a string) both in game log and the mod log.

        FMLLog.log(ModProperties.NAME, level, String.valueOf(object));
    }

    public static String format(String format, Object... args) {
        return new Formatter().format(format, args).toString();
    }


    // Shortcuts of the log method, but without having to specify the level and import the class.
    public static void all(Object object)   { log(Level.ALL, object); }
    public static void debug(Object object) { log(Level.DEBUG, object); }
    public static void error(Object object) { log(Level.ERROR, object); }
    public static void fatal(Object object) { log(Level.FATAL, object); }
    public static void info(Object object)  { log(Level.INFO, object); }
    public static void off(Object object)   { log(Level.OFF, object); }
    public static void trace(Object object) { log(Level.TRACE, object); }
    public static void warn(Object object)  { log(Level.WARN, object); }
}
