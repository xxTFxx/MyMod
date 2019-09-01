package com.xxTFxx.siberianadv.block.cables;

import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		TileEntity te = worldIn.getTileEntity(pos);
		
		if(te instanceof TE_Connector)
		{
			if(((TE_Connector) te).hasConnectionFrom())
			{
				BlockPos linkFrom = ((TE_Connector) te).getLinkFrom();
				if(linkFrom != null)
				{
					TileEntity tile = worldIn.getTileEntity(linkFrom);
					if(tile instanceof TE_Connector)
					{
						TE_Connector conn_from = (TE_Connector) tile;
						conn_from.changeConnectionToState(false);
						worldIn.notifyBlockUpdate(linkFrom, conn_from.getState(), conn_from.getState(), 3);						
					}
					
				}				
			}
			if(((TE_Connector) te).hasConnectionTo())
			{
				BlockPos linkTo = ((TE_Connector) te).getLinkTo();
				if(linkTo != null)
				{
					TileEntity tile = worldIn.getTileEntity(linkTo);
					if (tile instanceof TE_Connector)
					{
						TE_Connector conn_to = (TE_Connector) tile;
						((TE_Connector) tile).changeConnectionFromState(false);
						worldIn.notifyBlockUpdate(linkTo, ((TE_Connector) tile).getState(), ((TE_Connector) tile).getState(), 3);	
					}
				}
			}
		}
		
		super.breakBlock(worldIn, pos, state);
	}

}
