package com.xxTFxx.siberianadv.world.biomes;

import java.util.Random;

import com.xxTFxx.siberianadv.entity.EntitySiberiaBear;
import com.xxTFxx.siberianadv.entity.EntitySiberiaMan;
import com.xxTFxx.siberianadv.init.BiomeInit;
import com.xxTFxx.siberianadv.init.ModBlocks;
import com.xxTFxx.siberianadv.world.gen.WorldGenTaiga3;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeSiberia extends Biome{

	private boolean logged = false;

	protected static final WorldGenTaiga3 TREE = new WorldGenTaiga3(true);
	
	public BiomeSiberia(BiomeProperties properties) {
		super(properties.setBaseHeight(0.01F).setHeightVariation(0.05F).setTemperature(-0.5F).setRainfall(0.6F).setSnowEnabled());
		properties.setBaseHeight(0.01F);
		
		//topBlock = ModBlocks.SNOWM.getDefaultState();
		//fillerBlock = ModBlocks.SNOWM.getDefaultState();
		topBlock = Blocks.SNOW.getDefaultState();
		fillerBlock = Blocks.SNOW.getDefaultState();
		this.decorator.treesPerChunk = 4;
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySiberiaBear.class, 15, 1, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 5, 2, 3));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySiberiaMan.class, 94, 1, 3));
		
	}
	
	@Override
	public WorldGenTaiga3 getRandomTreeFeature(Random rand) {
		return TREE;
	}
	
	@Override
	public void genTerrainBlocks(final World worldIn, final Random random, final ChunkPrimer chunkPrimer, final int x, final int z, final double stoneNoise) {
		super.genTerrainBlocks(worldIn, random, chunkPrimer, x, z, stoneNoise);

		if (!logged) {
			logged = true;
			System.out.println("---------------------------Generating BIOME test at " + x + z);
		}
	}
	
}
