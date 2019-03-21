package com.xxTFxx.siberianadv.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.world.biomes.BiomeSiberia;
import com.xxTFxx.siberianadv.world.gen.generators.WorldGenStructure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator{

	public static final WorldGenStructure SHOUSE = new WorldGenStructure("shouse");
	public static final WorldGenStructure MINE1 = new WorldGenStructure("mine1");
	
		
		@Override
		public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
		{
			switch(world.provider.getDimension())
			{
			case 2:
				
				break;
				
			case 1:
				
				break;
				
			case 0:
				
				generateStructure(SHOUSE, SHOUSE.getName() ,world, random, chunkX, chunkZ, 100, Blocks.SNOW, BiomeSiberia.class , BiomeForest.class , BiomePlains.class , BiomeTaiga.class);
				generateStructure(MINE1, MINE1.getName() ,world, random, chunkX, chunkZ, 70, Blocks.SNOW, BiomeSiberia.class);
				
				break;
				
			case -1:
				
			}
		}
		
		private void generateStructure(WorldGenerator generator, String name ,World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
		{
			ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
			
			int x = (chunkX * 16) + random.nextInt(15) + 8;
			int z = (chunkZ * 16) + random.nextInt(15) + 8;
			int y = calculateGenerationHeight(world, x, z, topBlock);
			
			if(name.contentEquals("mine1"))
			{
				y -= 5;
			}
			
			BlockPos pos = new BlockPos(x,y,z);
			
			Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
			
			if(world.getWorldType() != WorldType.FLAT)
			{
				if(classesList.contains(biome))
				{
					if(random.nextInt(chance) == 0)
					{
						generator.generate(world, random, pos);
					}
				}
			}
		}
		
		public static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
		{
			int y = world.getHeight();
			boolean foundGround = false;
			
			while(!foundGround && y-- >= 0)
			{
				Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
				foundGround = block == topBlock;
			}
			
			return y;
		}
		
		
}
