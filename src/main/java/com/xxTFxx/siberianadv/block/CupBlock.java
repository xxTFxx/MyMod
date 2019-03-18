package com.xxTFxx.siberianadv.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CupBlock extends RotBlock{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final AxisAlignedBB CupAABB = new AxisAlignedBB(0.375D, 0D, 0.375D, 0.6875D , 0.375D, 0.6875D);
	public static final AxisAlignedBB CupAABBn = new AxisAlignedBB(0.3125D, 0D, 0.3125D, 0.75D , 0.375D, 0.625D);
	public static final AxisAlignedBB CupAABBe = new AxisAlignedBB(0.375D, 0D, 0.3125D, 0.6875D , 0.375D, 0.75D);
	public static final AxisAlignedBB CupAABBs = new AxisAlignedBB(0.25D, 0D, 0.375D, 0.6875D , 0.375D, 0.6875);
	public static final AxisAlignedBB CupAABBw = new AxisAlignedBB(0.3125D, 0D, 0.25D, 0.625D , 0.375D, 0.6875);




	public CupBlock(Material material, SoundType sound, String name, float hardness) {
		super(material, sound, name, hardness);
		
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = (EnumFacing)state.getValue(FACING);
		if(facing.equals(EnumFacing.NORTH))
		{
			return CupAABBn;			
		}
		else if(facing.equals(EnumFacing.EAST))
		{
			return CupAABBe;
		}
		else if(facing.equals(EnumFacing.SOUTH))
		{
			return CupAABBs;
		}
		else if(facing.equals(EnumFacing.WEST))
		{
			return CupAABBw;
		}
		return null;
	}	


}
