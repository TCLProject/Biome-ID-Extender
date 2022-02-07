package net.tclproject.mysteriumlib.asm.fixes;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;

public class MysteriumPatchesFixLoaderBiome extends CustomLoadingPlugin {

    // Turns on MysteriumASM Lib. You can do this in only one of your Fix Loaders.

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    @Override
    public void registerFixes() {
        //Registers the classes where the methods with the @Fix annotations are
    	registerClassWithFixes("net.tclproject.mysteriumlib.asm.fixes.MysteriumPatchesFixesBiomes");
    }
}
