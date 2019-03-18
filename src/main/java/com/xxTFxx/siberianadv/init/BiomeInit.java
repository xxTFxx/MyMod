package com.xxTFxx.siberianadv.init;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.world.biomes.BiomeSiberia;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Main.MOD_ID)
public class BiomeInit {
	
	public static final BiomeSiberia siberia = new BiomeSiberia(new Biome.BiomeProperties("Siberia"));

	/*public static void initRegisterBiomes() {
		initBiome(SIBERIA, "Siberia", BiomeType.COOL , Type.SNOWY , Type.COLD);
	}
	
	private static void initBiome(Biome biome , String name , BiomeType biomeType , Type... types ) {
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		System.out.println("Biome Registered");
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeEntry(biome , 1));
		BiomeManager.addSpawnBiome(biome);
		System.out.println("Biome added");
		//return biome;
	}*/
	
	@Mod.EventBusSubscriber(modid = Main.MOD_ID)
    public static class RegistrationHandler
    {

        @SubscribeEvent
        public static void onEvent(final RegistryEvent.Register<Biome> event)
        {
            final IForgeRegistry<Biome> registry = event.getRegistry();
            System.out.printf("asdasd");
            System.out.println("Registering biomes");
            
            //registry.register(new BiomeSiberia().setRegistryName(Main.MOD_ID, "siberia"));
            registerBiome(registry , siberia ,"siberia" , BiomeManager.BiomeType.WARM , 1000 , BiomeDictionary.Type.COLD , BiomeDictionary.Type.SNOWY , BiomeDictionary.Type.FOREST);
        }
    
	
	/*public static void initBiomeManagerAndDictionary()
    {
		System.out.println("biome added");
        BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(siberia, 10));
        BiomeManager.addSpawnBiome(siberia);
        BiomeDictionary.addTypes(siberia, 
                BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.FOREST
                );
        System.out.println("biome registered");
    }*/
	
	private static <T extends Biome> void registerBiome(final IForgeRegistry<Biome> registry, final T biome, final String biomeName, final BiomeManager.BiomeType biomeType, final int weight, final BiomeDictionary.Type... types) {
		registry.register(biome.setRegistryName(Main.MOD_ID, biomeName));
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));
		System.out.println("biome added");
	}
   }
	
}
