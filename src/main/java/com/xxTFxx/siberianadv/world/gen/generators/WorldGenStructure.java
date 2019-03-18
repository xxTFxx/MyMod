package com.xxTFxx.siberianadv.world.gen.generators;

import java.util.Map;
import java.util.Map.Entry;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.util.IStructure;
import com.xxTFxx.siberianadv.world.gen.WorldGenCustomStructures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenStructure extends WorldGenerator implements IStructure{

public String structureName;
	
	public WorldGenStructure(String name) 
	{
		this.structureName = name;
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) 
	{
		this.generateStructure(worldIn, rand ,position , getName());
		return true;
	}
	
	public void generateStructure(World world, Random rand , BlockPos pos , String name)
	{
		MinecraftServer mcServer = world.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = new ResourceLocation(Main.MOD_ID, structureName);
		Template template = manager.get(mcServer, location);
		
		if(isValid(world, pos, template , name)) {

					IBlockState state = world.getBlockState(pos);
					world.notifyBlockUpdate(pos, state, state, 3);
					template.addBlocksToWorldChunk(world, pos, settings);

				
					PlacementSettings placementsettings = (IStructure.settings);
					//if(structureName.equals("mine1")) {
						
						System.out.println("Generated at " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
										
						Map<BlockPos, String> map = template.getDataBlocks(pos, placementsettings);
						for(Entry<BlockPos , String> entry : map.entrySet())
						{
							if("chest".equals(entry.getValue())) {
								BlockPos pos2 = entry.getKey();
								world.setBlockState(pos2, Blocks.AIR.getDefaultState() , 3);
								TileEntity tileEntity = world.getTileEntity(pos2.down());
								if(tileEntity instanceof TileEntityChest) {
									
									((TileEntityChest)tileEntity).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON , rand.nextLong());
								}
								
						}
					}
				}
		
		}
	//}
	
	public String getName() {
		return structureName;
	}
	
	private boolean isValid(World world , BlockPos pos , Template template , String name)
	{
		int y = pos.getY() + 1;
		
		if(name.equals("mine1")) {
			y += 5;
		}
		int x1 = template.getSize().getX() + pos.getX();
		int z1 = template.getSize().getZ() + pos.getZ();
		int y1 = WorldGenCustomStructures.calculateGenerationHeight(world, x1, z1, Blocks.GRASS);

		//System.out.println(" X " + pos.getX() + " Y " + y + " Z " + pos.getZ() + " X1 " + x1 + " Y1 " + y1 + " Z1 " + z1);
		
		if(y1 > (y+2) || y < (y-2)) {
			return false;
		}
		else {
			return true;
		}
		
	}


	
}
