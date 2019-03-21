package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.tabs.TEOilPump;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OilPump extends BasicBlock{
	
	public OilPump(String name) {
		super(Material.IRON, name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEOilPump();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TEOilPump tile = (TEOilPump)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote)
		{
			System.out.println(tile.getFluidAmount());
			
		}
		return true;
	}

}
