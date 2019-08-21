package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.tileentity.TE_ConcreteMixer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ConcreteMixer extends RotBlock{
	
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 2.0D, 2.0D, 1.0D);

	public ConcreteMixer(String name) {
		super(Material.IRON, name, true);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TE_ConcreteMixer();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_CONCRETE_MIXER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
		
	}
	
}
