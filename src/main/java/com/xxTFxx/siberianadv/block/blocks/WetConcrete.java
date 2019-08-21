package com.xxTFxx.siberianadv.block.blocks;

import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tileentity.TE_WetConcrete;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WetConcrete extends BasicBlock{
	
	public WetConcrete(String name) {
		super(Material.ROCK, SoundType.STONE , name);
	}
	
	public static void changeState(World worldIn, BlockPos pos)
	{
		worldIn.removeTileEntity(pos);
		worldIn.setBlockState(pos, BlockInit.CONCRETE.getDefaultState());
		
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TE_WetConcrete();
	}

}
