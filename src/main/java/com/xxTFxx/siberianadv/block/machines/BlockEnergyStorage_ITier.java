package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tileentity.TileEntityEnergyStorage_ITier;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyStorage_ITier extends BasicBlock{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public BlockEnergyStorage_ITier(String name) {
		super(Material.IRON , name);
		BlockInit.blocks.add(this);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_ENERGYSTORAGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
		
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEnergyStorage_ITier();
	}
	
}
