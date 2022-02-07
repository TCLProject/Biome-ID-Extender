# Biome ID Extender

Biome ID Extender is a minecraft mod for Minecraft Forge 1.7.10 that aims to extend the biome ID limit to allow for more than 256 biomes in the game.

## Supported Mods

- Biomes O' Plenty
- Highlands
- ExtraBiomesXL
- Alternate Terrain Generation
- Realistic Terrain Generation
- Ridiculous World
- Thaumcraft
- ChromatiCraft

## Unsupported Mods

Let's hope this list will stay small.

- Enhanced Biomes: EB does some wacky stuff with it's worldgen beyond simply relying on the 256 limit; I tried to fix it but in good faith couldn't. Feel free to open a PR if you want to.
- RWG (Realistic World Gen, not to be confused with Realistic Terrain Generation): RWG is incompatible with the vast majority of biome mods, so there's no point in allowing RWG ids to go over 255 - 255 is more than enough for it.
- Any mod which adds a dimension and custom biomes to it: Because if you have ids higher 127 for these mods, their biomes spawn in the overworld.

## IMPORTANT

Old worlds created without Biome ID Extender will be incompatible with it installed.

Some biomes may display wrong biome name in F3. Don't worry, the biome generation itself isn't affected by this.

Is mod x (not listed above) supported? Try and see. There's a chance that no further compatibility patches are needed. 
If it crashes, you can suggest it and I'll see what I can do, but first make sure it's not in the "Unsupported" list.

## Installation to Game

1. Install Minecraft 1.7.10 with the **latest** Forge version.
2. Download and move the latest jar file from this mod's releases into the mods folder.

## Setting up a Development Environment

You can set up a devevelopment environment by Gradle, with IntelliJIdea: `gradlew setupDecompWorkspace idea genIntellijRuns` or Eclipse: `gradlew setupDecompWorkspace eclipse` then import the project. Complete the running configurations with the following VM option: `-Dfml.coreMods.load=net.tclproject.mysteriumlib.asm.fixes.MysteriumPatchesFixLoaderBiome`

## Contributing
Pull requests are welcome.

## License
This mod is currently licensed under the MIT license.
