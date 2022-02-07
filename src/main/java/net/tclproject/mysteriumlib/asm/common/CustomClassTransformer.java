package net.tclproject.mysteriumlib.asm.common;

import cpw.mods.fml.common.Loader;
import net.minecraft.launchwrapper.IClassTransformer;
import net.tclproject.biomeidextender.asm.ClassEdit;
import net.tclproject.mysteriumlib.asm.core.*;

import org.objectweb.asm.ClassWriter;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is responsible for inserting fixes into minecraft code 
 * from the moment when forge deobfuscation was applied.
 * */
public class CustomClassTransformer extends TargetClassTransformer implements IClassTransformer {
	
	/**An instance of itself that gets created every time the cunstructor gets called.*/
	static CustomClassTransformer instance;
	
	/**A map of "method index" : "mcp method name" for all the methods in methods.csv.*/
    private Map<Integer, String> methodsMap;

    /**Transformers that will be executed after all the normal ones are.*/
    private static List<IClassTransformer> postTransformers = new ArrayList<IClassTransformer>();

    public CustomClassTransformer() {
        instance = this;

        if (CustomLoadingPlugin.isObfuscated()) { // If running in obf environment, load obf method names
            try {
                long timeStart = System.currentTimeMillis();
                methodsMap = loadMethods();
                long time = System.currentTimeMillis() - timeStart;
                logger.debug("Methods dictionary loaded in " + time + " ms");
            } catch (IOException e) {
                logger.severe("Can not load obfuscated method names", e);
            }
        }

        this.metaReader = CustomLoadingPlugin.getMetaReader();

        this.fixesMap.putAll(FirstClassTransformer.instance.getFixesMap()); // Puts all fixes loaded in FirstClassTransformer into this class.
        FirstClassTransformer.instance.getFixesMap().clear();
        FirstClassTransformer.instance.registeredBuiltinFixes = true;
    }

    /**
     * Loads method indexes and obfuscated method names from a methods.bin file.
     * @throws IOException if the methods.bin file is not found.
     * @return A HashMap of "method index" : "mcp method name" for all the methods in methods.csv.
     * */
    private HashMap<Integer, String> loadMethods() throws IOException {
        InputStream resourceStream = getClass().getResourceAsStream("/methods.bin");
        if (resourceStream == null) throw new IOException("Methods dictionary not found.");
        DataInputStream input = new DataInputStream(new BufferedInputStream(resourceStream));
        int numMethods = input.readInt();
        HashMap<Integer, String> map = new HashMap<Integer, String>(numMethods);
        for (int i = 0; i < numMethods; i++) {
            map.put(input.readInt(), input.readUTF());
        }
        input.close();
        return map;
    }

    /**
     * Inserts fixes into classes passed in. First, runs the class through the normal transformer,
     * and only then, through the post transformers (ones that are to be executed after the normal ones).
     * <p/>
     * Forge passes in the arguments and takes the return value of this method, unless you want some
     * special behavior you shouldn't interact with this method or modify it.
     * */
    @Override
    public byte[] transform(String name, String deobfName, byte[] bytecode) {
        bytecode = transform(deobfName, bytecode);
        for (int i = 0; i < postTransformers.size(); i++) {
            bytecode = postTransformers.get(i).transform(name, deobfName, bytecode);
        }

        return !Arrays.asList(allNames).contains(deobfName) ? bytecode : FirstClassTransformer.mainInstance.transform(name, deobfName, bytecode);
    }

    public String[] allNames = {
            "rtg.world.biome.realistic.thaumcraft.RealisticBiomeTCBase",
            "rtg.world.biome.realistic.ridiculousworld.RealisticBiomeRWBase",
            "rtg.world.biome.realistic.enhancedbiomes.RealisticBiomeEBBase",
            "rtg.world.biome.realistic.atg.RealisticBiomeATGBase",
            "rtg.world.biome.realistic.chromaticraft.RealisticBiomeCCBase",
            "net.minecraft.network.play.server.S26PacketMapChunkBulk", "Reika.DragonAPI.Extras.IDType", "ttftcuts.atg.feature.ATGWorldGenRocks", "ttftcuts.atg.gen.ATGBiomeManager", "ttftcuts.atg.config.configfiles.ATGBiomeConfig", "enhancedbiomes.world.gen.layer.GenLayerEBRiverMix", "enhancedbiomes.world.gen.layer.GenLayerEBHills", "rtg.world.gen.LandscapeGenerator", "rtg.world.gen.ChunkProviderRTG", "net.tclproject.biomeidextender.BiomeUtils", "net.minecraft.world.chunk.storage.AnvilChunkLoader", "net.minecraft.world.chunk.Chunk", "net.minecraft.world.chunk.storage.ExtendedBlockStorage", "net.minecraft.client.network.NetHandlerPlayClient", "net.minecraft.network.play.server.S21PacketChunkData", "net.minecraft.world.gen.ChunkProviderEnd", "net.minecraft.world.gen.ChunkProviderFlat", "net.minecraft.world.gen.ChunkProviderGenerate", "net.minecraft.world.gen.ChunkProviderHell", "net.minecraft.world.biome.BiomeGenBase", "net.minecraft.world.gen.layer.GenLayerRiverMix", "net.minecraft.world.gen.layer.GenLayerVoronoiZoom", "biomesoplenty.common.world.layer.GenLayerRiverMixBOP", "biomesoplenty.common.world.BOPBiomeManager"};

    /**
     * Creates a custom Class Visitor to return custom method visitors to insert fixes. 
     * Has custom logic to check if a method is the target method of a fix, accounting for an obfuscated name.
     * */
    @Override
    public FixInserterClassVisitor createInserterClassVisitor(ClassWriter classWriter, List<ASMFix> fixes) {
        return new FixInserterClassVisitor(this, classWriter, fixes) {
            @Override
            protected boolean isTheTarget(ASMFix fix, String name, String descriptor) {
                if (CustomLoadingPlugin.isObfuscated()) {
                    String deobfName = methodsMap.get(getMethodIndex(name));
                    if (deobfName != null && super.isTheTarget(fix, deobfName, descriptor)) {
                        return true;
                    }
                }
                return super.isTheTarget(fix, name, descriptor);
            }
        };
    }

    /**Getter for methodsMap.*/
    public Map<Integer, String> getMethodNames() {
        return methodsMap;
    }

    /**
     * Gets a method index from a method name.
     * @return the method index (or -1 if it's not found).
     * */
    public static int getMethodIndex(String srgName) {
        if (srgName.startsWith("func_")) {
            int first = srgName.indexOf('_');
            int second = srgName.indexOf('_', first + 1);
            return Integer.valueOf(srgName.substring(first + 1, second));
        } else {
            return -1;
        }
    }

    /**
     * Registers a transformer that will be executed after the normal transformers (including the deobfuscation transformer).
     */
    public static void registerPostTransformer(IClassTransformer transformer) {
        postTransformers.add(transformer);
    }
}
