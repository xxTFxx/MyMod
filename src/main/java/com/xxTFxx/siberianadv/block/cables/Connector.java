package com.xxTFxx.siberianadv.block.cables;

import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Connector extends BasicBlock{
	
	public Connector(String name) {
		super(Material.ROCK, name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TE_Connector();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

}
